package br.com.vivo.sra.util;

import java.time.LocalDateTime;

public class CalculaTempo {
	
	
	public static int Diferenca(String horaInicio, String horaFim) {
		int horas = 0, minuto = 0;
		if(horaInicio != null && horaInicio != "" && horaFim != null && horaFim != "") {
			LocalDateTime  horaMinuto1 = LocalDateTime.parse(horaInicio);
			LocalDateTime  horaMinuto2 = LocalDateTime.parse(horaFim);
			
			horas = horaMinuto2.getHour() - horaMinuto1.getHour();
			if(horas == 0) {
				minuto = horaMinuto2.getMinute() - horaMinuto1.getMinute();
			}
		}	
		return minuto;
	}
}
