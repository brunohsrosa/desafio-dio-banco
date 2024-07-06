import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Map<String, List<Conta>> banco = new HashMap<>();

        int opcaoInicial;
        do {
            clearConsole();
            System.out.println();
            System.out.println("=== Sistema Bancário ===");
            System.out.println("1. Acessar Conta");
            System.out.println("2. Cadastrar Novo Cliente/Conta");
            System.out.println("3. Listar Clientes");
            System.out.println("4. Sair");
            System.out.print("Escolha uma opção: ");
            opcaoInicial = scanner.nextInt();
            scanner.nextLine(); // Limpar o buffer após ler o número
            
            switch (opcaoInicial) {
                case 1:
                    acessarConta(scanner, banco);
                    break;
                case 2:
                    cadastrarNovoClienteOuConta(scanner, banco);
                    break;
                case 3:
                    listarClientes(banco);
                    break;
                case 4:
                	System.out.println();
                    System.out.println("Saindo...");
                    break;
                default:
                	System.out.println();
                    System.out.println("Opção inválida. Tente novamente.");
                    break;
            }
            
            /*if (opcaoInicial != 4) {
                System.out.println("Pressione Enter para continuar...");
                scanner.nextLine(); // Espera a confirmação do usuário para continuar
            }*/
        } while (opcaoInicial != 4);

        scanner.close();
    }

    public static void acessarConta(Scanner scanner, Map<String, List<Conta>> banco) {
    	System.out.println();
        System.out.print("Digite o nome do cliente: ");
        String nomeCliente = scanner.nextLine();
        if (banco.containsKey(nomeCliente)) {
            List<Conta> contasCliente = banco.get(nomeCliente);
            System.out.println();
            System.out.println("Contas disponíveis para " + nomeCliente + ":");
            for (int i = 0; i < contasCliente.size(); i++) {
                System.out.println((i + 1) + ". " + contasCliente.get(i).getClass().getSimpleName() + " (Número: " + contasCliente.get(i).getNumero() + ")");
            }
            System.out.println();
            System.out.print("Escolha a conta que deseja acessar: ");
            int escolhaConta = scanner.nextInt();
            scanner.nextLine(); // Limpar o buffer após ler o número
            if (escolhaConta > 0 && escolhaConta <= contasCliente.size()) {
                Conta contaSelecionada = contasCliente.get(escolhaConta - 1);
                exibirMenuConta(scanner, contaSelecionada, banco, nomeCliente);
            } else {
            	System.out.println();
                System.out.println("Opção inválida.");
            }
        } else {
        	System.out.println();
            System.out.println("Cliente não encontrado.");
        }
    }

    public static void cadastrarNovoClienteOuConta(Scanner scanner, Map<String, List<Conta>> banco) {
    	System.out.println();
        System.out.print("Digite o nome do cliente: ");
        String nomeCliente = scanner.nextLine();
        Cliente cliente;
        List<Conta> contas;

        if (banco.containsKey(nomeCliente)) {
            cliente = new Cliente();
            cliente.setNome(nomeCliente);
            contas = banco.get(nomeCliente);
        } else {
            cliente = new Cliente();
            cliente.setNome(nomeCliente);
            contas = new ArrayList<>();
        }
        System.out.println();
        System.out.println("Criar uma nova conta para " + nomeCliente + ":");
        System.out.println("1. Conta Corrente");
        System.out.println("2. Conta Poupança");
        System.out.print("Escolha o tipo de conta: ");
        int tipoConta = scanner.nextInt();
        scanner.nextLine(); // Limpar o buffer após ler o número
        Conta novaConta;
        if (tipoConta == 1) {
            novaConta = new ContaCorrente(cliente);
        } else if (tipoConta == 2) {
            novaConta = new ContaPoupanca(cliente);
        } else {
        	System.out.println();
            System.out.println("Opção inválida.");
            return;
        }
        contas.add(novaConta);
        banco.put(nomeCliente, contas);
        System.out.println();
        System.out.println("Conta criada com sucesso!");
    }

    public static void listarClientes(Map<String, List<Conta>> banco) {
        if (banco.isEmpty()) {
        	System.out.println();
            System.out.println("Nenhum cliente cadastrado.");
        } else {
        	System.out.println();
            System.out.println("=== Lista de Clientes ===");
            for (String nomeCliente : banco.keySet()) {
                System.out.println(nomeCliente);
            }
        }
    }

    public static void exibirMenuConta(Scanner scanner, Conta conta, Map<String, List<Conta>> banco, String nomeCliente) {
        int opcao;
        
        do {
            clearConsole();
            System.out.println();
            System.out.println("=== Menu da Conta ===");
            System.out.println("1. Consultar Saldo");
            System.out.println("2. Sacar");
            System.out.println("3. Depositar");
            System.out.println("4. Transferir");
            System.out.println("5. Imprimir Extrato");
            System.out.println("6. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");
            
            opcao = scanner.nextInt();
            scanner.nextLine(); // Limpar o buffer após ler o número
            
            switch (opcao) {
                case 1:
                	System.out.println();
                    System.out.println("Saldo: " + conta.getSaldo());
                    break;
                case 2:
                	System.out.println();
                    System.out.print("Digite o valor para sacar: ");
                    double valorSaque = scanner.nextDouble();
                    conta.sacar(valorSaque);
                    break;
                case 3:
                	System.out.println();
                    System.out.print("Digite o valor para depositar: ");
                    double valorDeposito = scanner.nextDouble();
                    conta.depositar(valorDeposito);
                    break;
                case 4:
                	System.out.println();
                    System.out.print("Digite o nome do cliente para transferir: ");
                    String nomeDestino = scanner.nextLine();
                    if (banco.containsKey(nomeDestino)) {
                        List<Conta> contasDestino = banco.get(nomeDestino);
                        System.out.println("Contas disponíveis para " + nomeDestino + ":");
                        for (int i = 0; i < contasDestino.size(); i++) {
                            System.out.println((i + 1) + ". " + contasDestino.get(i).getClass().getSimpleName() + " (Número: " + contasDestino.get(i).getNumero() + ")");
                        }
                        System.out.println();
                        System.out.print("Escolha a conta para transferir: ");
                        int escolhaContaDestino = scanner.nextInt();
                        scanner.nextLine(); // Limpar o buffer após ler o número
                        if (escolhaContaDestino > 0 && escolhaContaDestino <= contasDestino.size()) {
                            Conta contaDestino = contasDestino.get(escolhaContaDestino - 1);
                            System.out.println();
                            System.out.print("Digite o valor para transferir: ");
                            double valorTransferencia = scanner.nextDouble();
                            conta.transferir(valorTransferencia, contaDestino);
                        } else {
                        	System.out.println();
                            System.out.println("Opção inválida.");
                        }
                    } else {
                    	System.out.println();
                        System.out.println("Cliente não encontrado.");
                    }
                    break;
                case 5:
                    conta.imprimirExtrato();
                    break;
                case 6:
                	System.out.println();
                    System.out.println("Voltando ao menu principal...");
                    break;
                default:
                	System.out.println();
                    System.out.println("Opção inválida. Tente novamente.");
                    break;
            }
            
            /*if (opcao != 6) {
                System.out.println("Pressione Enter para continuar...");
                scanner.nextLine(); // Espera a confirmação do usuário para continuar
            }*/
            
        } while (opcao != 6);
    }

    public static void clearConsole() {
        try {
            final String os = System.getProperty("os.name");
            
            if (os.contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}




