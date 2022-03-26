package modelo;

import java.util.Date;

public class Matricula {
	
	private Aluno aluno;
	private Date data;
	private double valorPago;
	private Curso curso;
	private String turma;
	
	
	public Matricula(Aluno a, Date d, double v, Curso c, String t) {
		aluno = a; 
		data = d;
		valorPago = v;
		curso = c; 
		turma = t;
	}

	
//Esse m�todo faz mais sentido na camada de controle
	public void matricular() {
		int qtd;
		Curso c = this.getCurso();
		qtd = c.getNumAlunos();
		c.setAluno(this, qtd);
		c.setNumAlunos(qtd+1);
	}


	public Aluno getAluno() {
		return aluno;
	}


	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}


	public Date getData() {
		return data;
	}


	public void setData(Date data) {
		this.data = data;
	}


	public double getValorPago() {
		return valorPago;
	}


	public void setValorPago(double valorPago) {
		this.valorPago = valorPago;
	}


	public Curso getCurso() {
		return curso;
	}


	public void setCurso(Curso curso) {
		this.curso = curso;
	}


	public String getTurma() {
		return turma;
	}


	public void setTurma(String turma) {
		this.turma = turma;
	}

	@Override
	public String toString() {
		return "Matricula do aluno " + aluno + ", o valorPago foi de R$ " + valorPago + 
				" referente ao curso " + curso.getNome();
	}

	
}
