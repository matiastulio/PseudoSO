package modulos;

import models.ProcessoVO;

public class ModuloRecursos {
	private int scanner;
	private int modem;
	private int impressoras[];
	private int[] satas;
	
	private int RECURSO_LIVRE = -1;

	public ModuloRecursos() {
		this.scanner = RECURSO_LIVRE;
		this.modem = RECURSO_LIVRE;
		this.impressoras = new int[2];
		impressoras[0] = RECURSO_LIVRE;
		impressoras[1] = RECURSO_LIVRE;
		this.satas = new int[2];
		satas[0] = RECURSO_LIVRE;
		satas[1] = RECURSO_LIVRE;
	}
	public enum RecursoReturn{
		ERR_MODEM,ERR_SCANNER,ERR_IMP1,ERR_IMP2,ERR_SATA1,ERR_SATA2,OK,ERR
	}
	
	public RecursoReturn alocaTodosOsRecursosParaProcesso(ProcessoVO pr) {
		if (pr.getReqModem() > 0) {
			if (modem == RECURSO_LIVRE) {
				modem = pr.getPID();// modem alocado para o processoID
			} else {
				return RecursoReturn.ERR_MODEM;
			}
		}
		if (pr.getReqScanner() > 0) {
			if (scanner == RECURSO_LIVRE) {
				scanner = pr.getPID();// scanner alocado para o processoID
			} else {
				desacolaTodosOsRecursosDoProcesso(pr);
				return RecursoReturn.ERR_SCANNER;
			}
		}
		if (pr.getReqCodImpressora() == 1) {
			if (impressoras[0] == RECURSO_LIVRE) {
				impressoras[0] = pr.getPID();// impressora 0 alocada para o processoID
			} else {
				desacolaTodosOsRecursosDoProcesso(pr);
				return RecursoReturn.ERR_IMP1;
			}
		} else if (pr.getReqCodImpressora() == 2) {
			if (impressoras[1] == RECURSO_LIVRE) {
				impressoras[1] = pr.getPID();// impressora 1 alocada para o processoID
			} else {
				desacolaTodosOsRecursosDoProcesso(pr);
				return RecursoReturn.ERR_IMP2;
			}
		}
		if (pr.getReqCodDisco() == 1) {
			if (satas[0] == RECURSO_LIVRE) {
				satas[0] = pr.getPID();// sata 0 alocado para o processoID
			} else {
				desacolaTodosOsRecursosDoProcesso(pr);
				return RecursoReturn.ERR_SATA1;
			}
		} else if (pr.getReqCodDisco() == 2) {
			if (satas[1] == RECURSO_LIVRE) {
				satas[1] = pr.getPID();// sata 1 alocado para o processoID
			} else {
				desacolaTodosOsRecursosDoProcesso(pr);
				return RecursoReturn.ERR_SATA2;
			}
		}
		pr.setRecursosAlocados(true);
		return RecursoReturn.OK;
	}

	public boolean desacolaTodosOsRecursosDoProcesso(ProcessoVO pr) {
		if (pr.getReqModem() > 0 && modem == pr.getPID()) {
			modem = RECURSO_LIVRE;// desaloca
		}

		if (pr.getReqScanner() > 0 && scanner == pr.getPID()) {
			scanner = RECURSO_LIVRE;// desaloca
		}

		if (pr.getReqCodImpressora() == 1 && impressoras[0] == pr.getPID()) {
			impressoras[0] = RECURSO_LIVRE;// desaloca
		} else if (pr.getReqCodImpressora() == 2 && impressoras[1] == pr.getPID()) {
			impressoras[1] = RECURSO_LIVRE;// desaloca
		}

		if (pr.getReqCodDisco() == 1 && satas[0] == pr.getPID()) {
			satas[0] = RECURSO_LIVRE;// desaloca
		} else if (pr.getReqCodDisco() == 2 && satas[1] == pr.getPID()) {
			satas[1] = RECURSO_LIVRE;// desaloca
		}

		pr.setRecursosAlocados(false);
		return true;
	}
	
	public int getProcessoFromRecursoError(RecursoReturn retorno){
		//ERR_MODEM,ERR_SCANNER,ERR_IMP1,ERR_IMP2,ERR_SATA1,ERR_SATA2,OK,ERR
		switch (retorno) {
		case ERR_MODEM:
			return modem;
		case ERR_SCANNER:
			return scanner;
		case ERR_IMP1:
			return impressoras[0];
		case ERR_IMP2:
			return impressoras[1];
		case ERR_SATA1:
			return satas[0];
		case ERR_SATA2:
			return satas[0];
		default:
			break;
		}
		
		return -1;
	}
}
