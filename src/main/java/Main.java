public class Main {
    public static void main(String[] args) {

        PessoaFisica F1 = new PessoaFisica("João", "Av. Antônio Carlos", "112.345.708-22", 35, 'm');
        Conta contaF1 = new ContaCorrente(1234, F1, 5000, 2000);

        try {
            contaF1.depositar(-2000);
        }catch(ValorNegativoException i){
            System.out.println(i.getMessage());
        }

        try{
            contaF1.sacar(-200);
        }catch (ValorNegativoException | SemLimiteException i){
            System.out.println(i.getMessage());
        }

        try{
            contaF1.sacar(2001);
        }catch (ValorNegativoException | SemLimiteException i){
            System.out.println(i.getMessage());
        }

        contaF1.setLimite(-106);

    }
}
