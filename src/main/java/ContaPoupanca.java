import java.io.Serializable;

public class ContaPoupanca extends Conta implements ITaxas {

    public ContaPoupanca(int agencia, int numero, Cliente dono, double saldo, double limite) {
        super(agencia, numero, dono, saldo, limite);
    }
    public boolean setLimite(double limite){
        if (limite < 100 || limite >1000) {
            throw new IllegalArgumentException("Limite inválido para conta poupança");
        } else {
            this.limite = limite;
            return true;
        }
    }

    @Override
    public double calculaTaxas() {
        return 0;
    }
}


