package com.loja.modelo;

import java.util.Objects;

public class Produto {
    private Integer id;
    private String nome;
    private double preco;
    private int qtdEstoque;
    private String categoria;

    // Construtor sem id
    public Produto(String nome, double preco, int qtdEstoque, String categoria) {
        this.nome = nome;
        this.preco = preco;
        this.qtdEstoque = qtdEstoque;
        this.categoria = categoria;
    }

    // Getters e Setters
    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public double getPreco() {
        return preco;
    }

    public int getQtdEstoque() {
        return qtdEstoque;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public void setQtdEstoque(int qtdEstoque) {
        this.qtdEstoque = qtdEstoque;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    // toString para exibição formatada
    @Override
    public String toString() {
        return "Id: " + getId() + "\nNome: " + getNome() + "\nPreço: " + getPreco() + "\nQuantidade de Estoque: " + getQtdEstoque() + "\nCategoria: " + getCategoria();
    }

    // equals e hashCode baseados no id
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Produto produto = (Produto) o;
        return Objects.equals(id, produto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
