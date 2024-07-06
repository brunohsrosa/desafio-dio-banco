public abstract class Conta implements iConta{
	
	private static final int AGENCIA_PADRAO = 1;
	private static int SEQUENCIAL = 1;
	
	protected int agencia;
	protected int numero;
	protected double saldo;
	protected Cliente cliente;
	
	public Conta(Cliente cliente) {
		this.agencia = Conta.AGENCIA_PADRAO;
		this.numero = SEQUENCIAL++;
		this.cliente = cliente;
	}	
	
	@Override
	public void sacar(double valor) {
		if (valor > 0 && saldo >= valor) {
			saldo -= valor;
			System.out.println("Saque realizado com sucesso.");
		} else {
			System.out.println("Saldo insuficiente para saque.");
		}
	}

	@Override
	public void depositar(double valor) {
		if (valor > 0) {
			saldo += valor;
			System.out.println("Depósito realizado com sucesso.");
		} else {
			System.out.println("Valor de depósito inválido.");
		}
	}

	@Override
	public void transferir(double valor, Conta contaDestino) {
		if (valor > 0 && saldo >= valor) {
			this.sacar(valor);
			contaDestino.depositar(valor);
			System.out.println("Transferência realizada com sucesso.");
		} else {
			System.out.println("Saldo insuficiente para transferência.");
		}
	}

	public int getAgencia() {
		return agencia;
	}

	public int getNumero() {
		return numero;
	}

	public double getSaldo() {
		return saldo;
	}
}
