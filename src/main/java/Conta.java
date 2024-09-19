
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public abstract class Conta implements ITaxas, Serializable {

    final private static long serialVertionUID = 1L;

    private int agencia;

    private int numero;

    private Cliente dono;

    private double saldo;

    protected double limite;

    private ArrayList<Operacao> operacoes;

    private transient int proximaOperacao;

    private transient static int totalContas = 0;

    public static int cont=0;

    public Conta(int agencia, int numero, Cliente dono, double saldo, double limite) {
        this.agencia = agencia;
        this.numero = numero;
        this.dono = dono;
        this.saldo = saldo;
        this.limite = limite;

        this.operacoes = new ArrayList<Operacao>();
        this.proximaOperacao = 0;

        Conta.totalContas++;
    }

    public void salvarDados()  {
        String fileName = this.agencia + "-" + this.numero + ".ser";
        FileOutputStream file = null;
        ObjectOutputStream object = null;

        try{
            file = new FileOutputStream(fileName);
            object =new ObjectOutputStream(file);
            object.writeObject(this);
            System.out.println("Dados da conta salvos!");
        }catch (IOException x) {
            System.err.println("Erro ao salvar os dados :(");
            System.err.println(x.getMessage());
        }finally {
            if(object != null){
                try{
                    object.close();
                }catch (IOException x){
                    System.err.println("Erro ao salvar os dados :(");
                    System.err.println(x.getMessage());
                }
            }
            if(file != null) {
                try{
                    file.close();
                }catch (IOException x){
                    System.err.println("Erro ao salvar os dados :(");
                    System.err.println(x.getMessage());
                }
            }

        }

    }

    public static Conta pegarDados(int numero, int agencia){
        String fileName = agencia + "-" + numero + ".ser";
        FileInputStream file = null;
        ObjectInputStream object = null;

        try{
            file = new FileInputStream(fileName);
            object =new ObjectInputStream(file);
            Conta conta = ((Conta) object.readObject());
            System.out.println("Dados da conta resgatados!");
            return conta;
        }catch (IOException  | ClassNotFoundException x) {
            System.err.println("Erro ao resgatar os dados :(");
            System.err.println(x.getMessage());
        }finally{
            if(object != null){
                try{
                    object.close();
                }catch (IOException x){
                    System.err.println("Erro ao resgatar os dados :(");
                    System.err.println(x.getMessage());
                }
            }
            if(file != null){
                try{
                    file.close();
                }catch (IOException x){
                    System.err.println("Erro ao resgatar os dados :(");
                    System.err.println(x.getMessage());
                }

            }
        }
        return null;
    }


    public boolean sacar(double valor) throws ValorNegativoException, SemLimiteException{

        if(valor < 0){
            throw new ValorNegativoException("Valor inválido:"+valor);
        }

        if(valor > this.getLimite()){
            throw new SemLimiteException("Valor acima do limite:"+valor);
        }

        if (valor >= 0 && valor <= this.limite) {
            this.saldo -= valor;
            this.operacoes.add (new OperacaoSaque(valor));
            cont++;
            return true;
        }

        return false;
    }

    public void depositar(double valor) throws ValorNegativoException{

        if (valor < 0 ){
            throw new ValorNegativoException("Valor inválido:"+ valor);
        }
        this.saldo += valor;
        this.operacoes.add(new OperacaoDeposito(valor));
        cont++;
    }

    public boolean transferir(Conta destino, double valor) throws ValorNegativoException,SemLimiteException{
        boolean valorSacado = this.sacar(valor);
        if (valorSacado) {
            destino.depositar(valor);
            return true;
        }
        return false;
    }

    public String toString() {
        String dados= "===== Conta: " + this.numero + " ====="+"\nDono: " + this.dono.getNome()+ "\nSaldo: " + this.saldo +
                "\nLimite: " + this.limite+ "\n====================";
        return dados;
    }

    public void imprimirExtrato(int flag) {
        if(flag == 1) {
            System.out.println("======= Extrato Conta: " + this.numero + " ======");
            for (Operacao atual : this.operacoes) {
                if (atual != null) {
                    System.out.println(atual.toString());
                }
            }
            System.out.println("====================");
        }else if(flag == 2){
            Collections.sort(operacoes);
            System.out.println("======= Extrato Conta : " + this.numero + " (Ordenado) ======");
            for (Operacao atual : this.operacoes) {
                if (atual != null) {
                    System.out.println(atual.toString());
                }
            }
            System.out.println("====================");
        }else{
            System.out.println("Forma de ordenação inválida");
        }
    }

    public boolean equals(Object object){
        if(object instanceof Conta){
            Conta Cnt= (Conta) object;
            if(this.numero == Cnt.numero){
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

    public void imprimirExtratoTaxas(){
        double total = 0;

        System.out.printf("====== Extrato de Taxas ======\n" +
                "Manutenção da conta: %.2f \n\n" +
                "Operações\n", calculaTaxas());
        total += calculaTaxas();
        for(Operacao atual : this.operacoes) {
            if (atual != null) {
                if(atual instanceof OperacaoSaque) {
                    System.out.printf("Saque: %.2f\n", atual.calculaTaxas());
                    total += atual.calculaTaxas();
                }
            }
        }
        System.out.printf("\nTotal: %.2f \n", total);
    }

    public int getNumero() {
        return numero;
    }

    public Cliente getDono() {
        return dono;
    }

    public double getSaldo() {
        return saldo;
    }

    public double getLimite() {
        return limite;
    }

    public static int getTotalContas() {
        return Conta.totalContas;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public void setDono(Cliente dono) {
        this.dono = dono;
    }

    public  int getAgencia() {
        return agencia;
    }

    public abstract boolean setLimite(double limite) throws IllegalArgumentException;
}

