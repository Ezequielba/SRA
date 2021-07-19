package br.com.vivo.sra.util;

import java.time.LocalDateTime;

public class CalculaTempo {
	
	
	public static int Diferenca(String horaInicio, String horaFim) {
		int horas = 0, minuto = 0;
		if(horaInicio != null && horaInicio != "" && horaFim != null && horaFim != "") {
			LocalDateTime  horaMinuto1 = LocalDateTime.parse(horaInicio);
			LocalDateTime  horaMinuto2 = LocalDateTime.parse(horaFim);
			LocalDateTime  Dia1 = LocalDateTime.parse(horaInicio);
			LocalDateTime  Dia2 = LocalDateTime.parse(horaFim);
			
			horas = horaMinuto2.getHour() - horaMinuto1.getHour();
			int dia = Dia2.getDayOfMonth() - Dia1.getDayOfMonth();

			if(dia > 0) {
				minuto = -1;
				return minuto;
			}
			else if(horas == 0) {
				minuto = horaMinuto2.getMinute() - horaMinuto1.getMinute();
			}
			else {
				minuto = -1;
			}
		}	
		return minuto;
	}
}
