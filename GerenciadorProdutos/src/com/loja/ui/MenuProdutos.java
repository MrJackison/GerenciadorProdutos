package com.loja.ui;

import com.loja.exception.ValidacaoException;
import com.loja.gerenciador.GerenciadorProdutos;
import com.loja.modelo.Produto;

import java.util.List;
import java.util.Scanner;

public class MenuProdutos {
    private Scanner scanner;
    private GerenciadorProdutos gerenciador;

    public MenuProdutos() {
        this.scanner = new Scanner(System.in);
        this.gerenciador = new GerenciadorProdutos();
    }

    // Exibe o menu e processa as escolhas do usuário
    public void exibirMenu() {
        while (true) {
            System.out.println("\n--- Menu de Gerenciamento de Produtos ---");
            System.out.println("1. Cadastrar Produto");
            System.out.println("2. Buscar Produto por ID");
            System.out.println("3. Listar Todos os Produtos");
            System.out.println("4. Atualizar Produto");
            System.out.println("5. Deletar Produto");
            System.out.println("6. Buscar Produto por Nome");
            System.out.println("7. Buscar Produto por Categoria");
            System.out.println("8. Sair");
            System.out.print("Escolha uma opção: ");

            int opcao = lerEntradaInteira("");

            switch (opcao) {
                case 1 -> cadastrarProduto();
                case 2 -> buscarProduto();
                case 3 -> listarProdutos();
                case 4 -> atualizarProduto();
                case 5 -> deletarProduto();
                case 6 -> buscarPorNome();
                case 7 -> buscarPorCategoria();
                case 8 -> {
                    System.out.println("Saindo do sistema...");
                    return;
                }
                default -> System.out.println("Opção inválida! Tente novamente.");
            }
        }
    }

    //Método de cadastro de produto na classe MenuProdutos
    private void cadastrarProduto() {
        System.out.println("\n--- Cadastro de Produto ---");

        //Solicitar nome
        String nome = lerEntradaString("Nome: ");

        //Solicitar preço
        double preco = lerEntradaDouble("Preço: ");

        //Solicitar quantidade
        int quantidade = lerEntradaInteira("Quantidade em Estoque: ");

        //Solicitar categoria
        String categoria = lerEntradaString("Categoria: ");

        //Validar dados e 6. Confirmar cadastro
        try {
            Produto produto = new Produto(nome, preco, quantidade, categoria);

            //Validação dos dados usando o método validarProduto do Gerenciador
            gerenciador.validarProduto(produto); // Supomos que este método lança exceções para dados inválidos

            //Confirmar o cadastro chamando o método criar do GerenciadorProdutos
            gerenciador.criar(produto);

            //Exibir resultado
            System.out.println("Produto cadastrado com sucesso!");
        } catch (ValidacaoException e) {
            System.out.println("Erro ao cadastrar produto: " + e.getMessage());
        }
    }


    //Busca e exibe um produto por ID
    private void buscarProduto() {
        System.out.println("\n--- Buscar Produto por ID ---");
        int id = lerEntradaInteira("ID do Produto: ");
        Produto produto = gerenciador.buscarPorId(id);

        if (produto != null) {
            System.out.println("Produto encontrado: " + produto);
        } else {
            System.out.println("Produto com ID " + id + " não encontrado.");
        }
    }

    //Lista todos os produtos cadastrados
    private void listarProdutos() {
        System.out.println("\n--- Lista de Produtos ---");
        List<Produto> produtos = gerenciador.listarTodos();

        if (produtos.isEmpty()) {
            System.out.println("Nenhum produto cadastrado.");
        } else {
            for (Produto produto : produtos) {
                System.out.println(produto);
            }
        }
    }

    //Método de atualização de produto na classe MenuProdutos
    private void atualizarProduto() {
        System.out.println("\n--- Atualizar Produto ---");

        //Buscar produto por ID
        int id = lerEntradaInteira("ID do Produto a ser atualizado: ");
        Produto produtoExistente = gerenciador.buscarPorId(id);

        if (produtoExistente != null) {
            //Exibir dados atuais
            System.out.println("Dados atuais do produto:");
            System.out.println("Nome: " + produtoExistente.getNome());
            System.out.println("Preço: " + produtoExistente.getPreco());
            System.out.println("Quantidade em Estoque: " + produtoExistente.getQtdEstoque());
            System.out.println("Categoria: " + produtoExistente.getCategoria());

            // Solicitar novos dados
            String novoNome = lerEntradaString("Novo Nome (deixe em branco para não alterar): ");
            double novoPreco = lerEntradaDouble("Novo Preço (digite um número negativo para não alterar): ");
            int novaQuantidade = lerEntradaInteira("Nova Quantidade em Estoque (digite um número negativo para não alterar): ");
            String novaCategoria = lerEntradaString("Nova Categoria (deixe em branco para não alterar): ");

            //Manter valores atuais se o usuário não quiser alterar
            novoNome = novoNome.isEmpty() ? produtoExistente.getNome() : novoNome;
            novoPreco = novoPreco < 0 ? produtoExistente.getPreco() : novoPreco;
            novaQuantidade = novaQuantidade < 0 ? produtoExistente.getQtdEstoque() : novaQuantidade;
            novaCategoria = novaCategoria.isEmpty() ? produtoExistente.getCategoria() : novaCategoria;

            //Validar alterações
            try {
                Produto produtoAtualizado = new Produto(novoNome, novoPreco, novaQuantidade, novaCategoria);
                produtoAtualizado.setId(id); // Mantém o mesmo ID

                // Validação dos dados
                gerenciador.validarProduto(produtoAtualizado);

                // 5. Confirmar atualização
                if (gerenciador.atualizar(produtoAtualizado)) {
                    System.out.println("Produto atualizado com sucesso!");
                } else {
                    System.out.println("Erro ao atualizar o produto.");
                }
            } catch (ValidacaoException e) {
                System.out.println("Erro de validação: " + e.getMessage());
            }
        } else {
            System.out.println("Produto com ID " + id + " não encontrado.");
        }
    }


    // Deleta um produto pelo ID
    private void deletarProduto() {
        System.out.println("\n--- Deletar Produto ---");
        int id = lerEntradaInteira("ID do Produto a ser deletado: ");

        if (gerenciador.deletar(id)) {
            System.out.println("Produto deletado com sucesso!");
        } else {
            System.out.println("Produto com ID " + id + " não encontrado.");
        }
    }

    // Busca produtos por nome
    private void buscarPorNome() {
        System.out.println("\n--- Buscar Produto por Nome ---");
        String nome = lerEntradaString("Nome do Produto: ");
        List<Produto> produtos = gerenciador.buscarPorNome(nome);

        if (produtos.isEmpty()) {
            System.out.println("Nenhum produto encontrado com o nome \"" + nome + "\".");
        } else {
            System.out.println("Produtos encontrados:");
            for (Produto produto : produtos) {
                System.out.println(produto);
            }
        }
    }

    // Busca produtos por categoria
    private void buscarPorCategoria() {
        System.out.println("\n--- Buscar Produto por Categoria ---");
        String categoria = lerEntradaString("Categoria do Produto: ");
        List<Produto> produtos = gerenciador.buscarPorCategoria(categoria);

        if (produtos.isEmpty()) {
            System.out.println("Nenhum produto encontrado na categoria \"" + categoria + "\".");
        } else {
            System.out.println("Produtos encontrados na categoria " + categoria + ":");
            for (Produto produto : produtos) {
                System.out.println(produto);
            }
        }
    }

    // Lê uma entrada de texto
    private String lerEntradaString(String mensagem) {
        System.out.print(mensagem);
        return scanner.nextLine().trim();
    }

    // Lê uma entrada de número double, com tratamento de erros
    private double lerEntradaDouble(String mensagem) {
        while (true) {
            try {
                System.out.print(mensagem);
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida! Digite um número decimal válido.");
            }
        }
    }

    // Lê uma entrada de número inteiro, com tratamento de erros
    private int lerEntradaInteira(String mensagem) {
        while (true) {
            try {
                System.out.print(mensagem);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida! Digite um número inteiro válido.");
            }
        }
    }
}
