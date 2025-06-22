package Model;

public class Livro {
    private int id;
    private String titulo;
    private String autor;
    private String ano;
    private String genero;
    private String imagem;

    // Construtor padrão
    public Livro() {}

    // Construtor com parâmetros
    public Livro(String titulo, String autor, String ano, String genero, String imagem) {
        this.titulo = titulo;
        this.autor = autor;
        this.ano = ano;
        this.genero = genero;
        this.imagem = imagem;
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }

    public String getAno() { return ano; }
    public void setAno(String ano) { this.ano = ano; }

    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }

    public String getImagem() { return imagem; }
    public void setImagem(String imagem) { this.imagem = imagem; }
} 