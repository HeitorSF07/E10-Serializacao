public class ContaCorrente extends Conta implements ITaxas{
    public ContaCorrente(int numero, Cliente dono, double saldo, double limite) {

        super(numero, dono, saldo, limite);
    }
    public boolean setLimite(double limite){
        if (limite < -100) {
            throw new IllegalArgumentException("Limite inválido para conta corrente");
        } else {
            this.limite = limite;
            return true;
        }
    }

    public double calculaTaxas() {
        if(getDono() instanceof PessoaFisica) {
            return 10;
        } else {
            return 20;
        }
    }
}




