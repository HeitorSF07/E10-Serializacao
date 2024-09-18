public class ContaUniversitaria extends Conta implements ITaxas{

    public ContaUniversitaria(int numero, Cliente dono, double saldo, double limite) {
        super(numero, dono, saldo, limite);
    }
    public boolean setLimite(double limite){
        if (limite < 0 || limite >500) {

            throw new IllegalArgumentException("Limite inválido para conta universitária");

        } else {
            this.limite = limite;
            return true;
        }
    }

    public double calculaTaxas() {
        return 0;
    }
}

