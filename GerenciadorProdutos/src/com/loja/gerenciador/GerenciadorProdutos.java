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

    /*
     * Construtor do gerenciador de produtos
     * Inicializa a lista de produtos e o contador do Id
     */
    public GerenciadorProdutos() {
        this.produtos = new ArrayList<>();
        this.proximoId = 1;
    }

    /*
     Cria e adiciona um novo produto
     Solicita um objeto do tipo Produto
     Aplica o método validarProduto no produto
     Atribui um id para o produto
     Adiciona o produto a lista de produtos
     */
    public void criar(Produto produto) {
        validarProduto(produto);
        produto.setId(proximoId++);
        produtos.add(produto);
    }

    /*
     Busca produto por ID
     Solicita um id do tipo int
     for-each loop percorrendo todos os elementos da lista produtos
     Verifica se o id do produto percorrido é igual ao id do parametro
     Se sim, retorna o produto
     Se não, retorna nulo
     */
    public Produto buscarPorId(int id) {
        for (Produto produto : produtos) {
            if (produto.getId() == id) {
                return produto;
            }
        }
        return null;
    }

    /*
    Lista todos os produtos
    Realiza um shallow copy por motivos de segurança
     */
    public List<Produto> listarTodos() {
        return new ArrayList<>(produtos);
    }

    /*
    Atualiza o produto
    Solicita o parameto do tipo produto (ProdutoAtualizado)
    for-each loop, novo obejeto produto recebe cada item da lista produtos
    Verifica se o id do produto é igual ao produtoAtualizado
    Aplica o método validarProduto no produtoAtualizado
    Substitiu o produto(i) pelo produtoAtualizado
    Retorna true se deu tudo certo
    Retorna false se deu o id não foi encontrado
     */
    public boolean atualizar(Produto produtoAtualizado) {
        for (int i = 0; i < produtos.size(); i++) {
            Produto produto = produtos.get(i);
            if (Objects.equals(produto.getId(), produtoAtualizado.getId())) {
                validarProduto(produtoAtualizado);
                produtos.set(i, produtoAtualizado);
                return true;
            }
        }
        return false;
    }

    /*
    Delata um produto
    Solicita um id int
    Utiliza uma lambida para verificar se o id do produto é igual ao parametro produto, retornando true se encontrar o id
     */
    public boolean deletar(int id) {
        return produtos.removeIf(produto -> produto.getId() == id);
    }

    /*
    Busca produtos pelo nome
    Solicita uma String nome
    Instancia uma lista do objeto Produto, inicializa um arrayList (encontrados)
    for-each loop padronizando o nome do produto, verificando se contem o parametro nome
    se sim, adiciona o produto a lista de encontrados
    retorna a lista encontrados
     */
    public List<Produto> buscarPorNome(String nome) {
        List<Produto> encontrados = new ArrayList<>();
        for (Produto produto : produtos) {
            if (produto.getNome().toLowerCase().contains(nome.toLowerCase())) {
                encontrados.add(produto);
            }
        }
        return encontrados;
    }

    /*
    Busca produtos por categoria
    Solicita um parametro string categoria
    Instancia uma lista do objeto Produto, inicializa um arrayList (encontrados)
    for-each loop padronizando a categoria do produto, verificando se é igual ao parametro categoria
    se sim, adiciona o produto a lista de encontrados
    retorna a lista encontrados
     */
    public List<Produto> buscarPorCategoria(String categoria) {
        List<Produto> encontrados = new ArrayList<>();
        for (Produto produto : produtos) {
            if (produto.getCategoria().equalsIgnoreCase(categoria)) {
                encontrados.add(produto);
            }
        }
        return encontrados;
    }

    /*
    Valida o produto
    verifica se o nome do produto é igual a null ou se o nome do produto em menos de dois caracteres
    lança ValidacaoException
    verifica se o preço do produto é igual menor que 0
    lança ValidacaoException
    verifica se a quantidade de estoque do produto é menor que 0
    lança ValidacaoException
    verifica se a categoria do produto é nula ou é vazia
    lança ValidacaoException
     */
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
