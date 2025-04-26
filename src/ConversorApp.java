import java.util.Scanner;

public class ConversorApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Conversor conversor = new Conversor();

        int opcao;
        do {
            conversor.exibirMenu();
            opcao = scanner.nextInt();

            if (opcao >= 1 && opcao <= 6) {
                System.out.println("Digite o valor que deseja converter:");
                double valor = scanner.nextDouble();
                System.out.println("*****************************************************************************");
                conversor.executarOpcao(opcao, valor);
                System.out.println("*****************************************************************************");
            } else if (opcao != 7) {
                System.out.println("Opção inválida! Tente novamente.");
            }

            if (opcao != 7) {
                System.out.println("\nPressione Enter para continuar...");
                scanner.nextLine(); // Limpar buffer
                scanner.nextLine(); // Esperar Enter
            }
        } while (opcao != 7);

        scanner.close();
    }
}
