package com.loja.gerenciador;

import com.loja.exception.ValidacaoException;
import com.loja.modelo.Produto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GerenciadorProdutos {
    private List<Produto> produtos;
    private int proximoId;

    private static int contador = 0;

    public GerenciadorProdutos() {
        this.produtos = new ArrayList<>();
        this.proximoId = 1;
    }

    //Cria e adiciona um novo produto
    public void criar(Produto produto) {
        validarProduto(produto);
        produto.setId(proximoId++); // Atribui o próximo ID e incrementa
        produtos.add(produto);
    }

    //Busca produto por ID
    public Produto buscarPorId(int id) {
        for (Produto produto : produtos) {
            if (produto.getId() == id) {
                return produto;
            }
        }
        return null; // Retorna null se o produto não for encontrado
    }

    // 3. Lista todos os produtos
    public List<Produto> listarTodos() {
        return new ArrayList<>(produtos); // Retorna uma nova lista para evitar modificações externas
    }

    //Atualiza um produto existente
    public boolean atualizar(Produto produtoAtualizado) {
        for (int i = 0; i < produtos.size(); i++) {
            Produto produto = produtos.get(i);
            if (Objects.equals(produto.getId(), produtoAtualizado.getId())) {
                validarProduto(produtoAtualizado);
                produtos.set(i, produtoAtualizado); // Atualiza o produto
                return true;
            }
        }
        return false; // Retorna false se o produto não for encontrado
    }

    //Deleta um produto pelo ID
    public boolean deletar(int id) {
        return produtos.removeIf(produto -> produto.getId() == id); // Remove e retorna true se encontrar
    }

    public List<Produto> buscarPorNome(String nome) {
        List<Produto> encontrados = new ArrayList<>();
        for (Produto produto : produtos) {
            if (produto.getNome().toLowerCase().contains(nome.toLowerCase())) {
                encontrados.add(produto);
            }
        }
        return encontrados;
    }

    //Busca produtos por categoria
    public List<Produto> buscarPorCategoria(String categoria) {
        List<Produto> encontrados = new ArrayList<>();
        for (Produto produto : produtos) {
            if (produto.getCategoria().equalsIgnoreCase(categoria)) {
                encontrados.add(produto);
            }
        }
        return encontrados;
    }

    public void validarProduto(Produto produto) throws ValidacaoException {
        if (produto.getNome() == null || produto.getNome().trim().length() < 2) {
            throw new ValidacaoException("Nome do produto deve ter pelo menos 2 caracteres.");
        }
        if (produto.getPreco() <= 0) {
            throw new ValidacaoException("Preço do produto deve ser maior que zero.");
        }
        if (produto.getQtdEstoque() < 0) {
            throw new ValidacaoException("Quantidade em estoque não pode ser negativa.");
        }
        if (produto.getCategoria() == null || produto.getCategoria().trim().isEmpty()) {
            throw new ValidacaoException("Categoria do produto não pode estar vazia.");
        }
    }

}
