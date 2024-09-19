import java.io.IOException;

public class Main {
    public static void main(String[] args) {

        PessoaFisica F1 = new PessoaFisica("João", "Av. Antônio Carlos", "112.345.708-22", 35, 'm');
        Conta contaF1 = new ContaCorrente(1, 1234, F1, 5000, 2000);

        contaF1.salvarDados();
        Conta dados = Conta.pegarDados(contaF1.getNumero(), contaF1.getAgencia());


    }
}
