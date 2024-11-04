package com.loja.Main;

import com.loja.exception.ValidacaoException;
import com.loja.gerenciador.GerenciadorProdutos;
import com.loja.modelo.Produto;

public class Main {
    public static void main(String[] args) {
        GerenciadorProdutos gerenciador = new GerenciadorProdutos();

        //Fluxo Principal
        System.out.println("=== Fluxo Principal ===");

        //Cadastrar 3 produtos
        try {
            System.out.println("Cadastrando produtos...");
            gerenciador.criar(new Produto("Caneta", 2.50, 100, "Escritório"));
            gerenciador.criar(new Produto("Caderno", 15.00, 50, "Escritório"));
            gerenciador.criar(new Produto("Calculadora", 25.00, 30, "Eletrônicos"));
            System.out.println("3 produtos cadastrados com sucesso.");
        } catch (ValidacaoException e) {
            System.out.println("Erro ao cadastrar produto: " + e.getMessage());
        }

        //Buscar por ID
        System.out.println("\nBuscando produto por ID 1:");
        Produto produtoBuscado = gerenciador.buscarPorId(1);
        if (produtoBuscado != null) {
            System.out.println("Produto encontrado: " + produtoBuscado);
        } else {
            System.out.println("Produto não encontrado.");
        }

        //Atualizar um produto
        System.out.println("\nAtualizando produto ID 2:");
        try {
            Produto produtoAtualizado = new Produto("Caderno Espiral", 18.00, 50, "Escritório");
            produtoAtualizado.setId(2); // Mantendo o mesmo ID
            gerenciador.atualizar(produtoAtualizado);
            System.out.println("Produto atualizado com sucesso.");
        } catch (ValidacaoException e) {
            System.out.println("Erro ao atualizar produto: " + e.getMessage());
        }

        //Listar todos
        System.out.println("\nListando todos os produtos:");
        gerenciador.listarTodos().forEach(System.out::println);

        //Deletar um produto
        System.out.println("\nDeletando produto ID 3:");
        if (gerenciador.deletar(3)) {
            System.out.println("Produto deletado com sucesso.");
        } else {
            System.out.println("Erro ao deletar o produto.");
        }

        //Casos de Erro
        System.out.println("\n=== Casos de Erro ===");

        //Buscar ID inexistente
        System.out.println("\nBuscando produto por ID 999:");
        Produto produtoInexistente = gerenciador.buscarPorId(999);
        if (produtoInexistente == null) {
            System.out.println("Produto com ID 999 não encontrado.");
        }

        //Cadastrar com dados inválidos
        try {
            System.out.println("Cadastrando produto com dados inválidos...");
            gerenciador.criar(new Produto("", -5.00, -10, ""));
        } catch (ValidacaoException e) {
            System.out.println("Erro esperado ao cadastrar produto: " + e.getMessage());
        }

        //Atualizar produto inexistente
        try {
            System.out.println("Atualizando produto inexistente...");
            Produto produtoAtualizarInexistente = new Produto("Produto Inexistente", 10.00, 5, "Teste");
            produtoAtualizarInexistente.setId(999); // ID que não existe
            gerenciador.atualizar(produtoAtualizarInexistente);
        } catch (ValidacaoException e) {
            System.out.println("Erro ao atualizar produto: " + e.getMessage());
        }

        //Deletar ID inexistente
        System.out.println("\nDeletando produto ID 999:");
        if (!gerenciador.deletar(999)) {
            System.out.println("Produto com ID 999 não encontrado para deletar.");
        }

        //Buscas
        System.out.println("\n=== Buscas ===");

        //Buscar por nome parcial
        System.out.println("\nBuscando produtos por nome parcial 'Can'");
        gerenciador.buscarPorNome("Can").forEach(System.out::println);

        //Buscar por categoria
        System.out.println("\nBuscando produtos por categoria 'Escritório'");
        gerenciador.buscarPorCategoria("Escritório").forEach(System.out::println);

        //Listar com lista vazia
        System.out.println("\nListando todos os produtos após deletar:");
        gerenciador.deletar(1); // Deletando um produto para deixar a lista com menos itens
        gerenciador.deletar(2); // Deletando o próximo
        gerenciador.listarTodos().forEach(System.out::println);
    }
}
