package models;

public class ProcessoVO {
	private int tempoInicializacao;
	private int prioridade;
	private int tempoProcessador;
	private int blocosEmMemoriaRAM;
	private int ReqCodImpressora;
	private int reqScanner;
	private int reqModem;
	private int reqCodDisco;
	private int PID; //quando o processo � iniciado o SO diz qual o ID do processo.
	
	
	
	
	public ProcessoVO(int tempoInicializacao, int prioridade, int tempoProcessador, int blocosEmMemoriaRAM,
			int reqCodImpressora, int reqScanner, int reqModem, int reqCodDisco) {

		this.tempoInicializacao = tempoInicializacao;
		this.prioridade = prioridade;
		this.tempoProcessador = tempoProcessador;
		this.blocosEmMemoriaRAM = blocosEmMemoriaRAM;
		this.ReqCodImpressora = reqCodImpressora;
		this.reqScanner = reqScanner;
		this.reqModem = reqModem;
		this.reqCodDisco = reqCodDisco;
	}
	
	public int getTempoInicializacao() {
		return tempoInicializacao;
	}
	public int getPrioridade() {
		return prioridade;
	}
	public int getTempoProcessador() {
		return tempoProcessador;
	}
	public int getBlocosEmMemoriaRAM() {
		return blocosEmMemoriaRAM;
	}
	public int getReqCodImpressora() {
		return ReqCodImpressora;
	}
	public int getReqScanner() {
		return reqScanner;
	}
	public int getReqModem() {
		return reqModem;
	}
	public int getReqCodDisco() {
		return reqCodDisco;
	}
	
	
}