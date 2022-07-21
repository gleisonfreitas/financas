package br.com.jetro.beans.validation;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import br.com.jetro.util.Util;

public class ValidadorCPF implements Validator{
	
	@Override
	public void validate(FacesContext context, UIComponent component, Object cpf) throws ValidatorException {
		
		 if (cpf != null && !validarCPF(String.valueOf(cpf))) {
             FacesMessage message = new FacesMessage();
             message.setSeverity(FacesMessage.SEVERITY_ERROR);
             message.setSummary("CPF inválido.");
             throw new ValidatorException(message);
        }
	}
	
	/** Realiza a validação do CPF. 
     * 
     * @param   strCPF número de CPF a ser validado 
     * @return  true se o CPF é válido e false se não é válido 
     */  
    public boolean validarCPF(String strCpf) {
    	strCpf = Util.removerFormatacao(strCpf);
    	if(strCpf.isEmpty()){
    		return true;
    	}
        if (strCpf.length() != 11 || verificarMesmoNumero(strCpf)) {  
            return false;  
        }  
        int d1, d2;  
        int digito1, digito2, resto;  
        int digitoCPF;  
        String nDigResult;  
        d1 = d2 = 0;  
        digito1 = digito2 = resto = 0;  
        for (int nCount = 1; nCount < strCpf.length() - 1; nCount++) {  
            digitoCPF = Integer.valueOf(strCpf.substring(nCount - 1, nCount)).intValue();  
            //multiplique a ultima casa por 2 a seguinte por 3 a seguinte por 4 e assim por diante.  
            d1 = d1 + (11 - nCount) * digitoCPF;  
            //para o segundo digito repita o procedimento incluindo o primeiro digito calculado no passo anterior.  
            d2 = d2 + (12 - nCount) * digitoCPF;  
        }  
        //Primeiro resto da divisão por 11.  
        resto = (d1 % 11);  
        //Se o resultado for 0 ou 1 o digito é 0 caso contrário o digito é 11 menos o resultado anterior.  
        if (resto < 2) {  
            digito1 = 0;  
        } else {  
            digito1 = 11 - resto;  
        }  
        d2 += 2 * digito1;  
        //Segundo resto da divisão por 11.  
        resto = (d2 % 11);  
        //Se o resultado for 0 ou 1 o digito é 0 caso contrário o digito é 11 menos o resultado anterior.  
        if (resto < 2) {  
            digito2 = 0;  
        } else {  
            digito2 = 11 - resto;  
        }  
        //Digito verificador do CPF que está sendo validado.  
        String nDigVerific = strCpf.substring(strCpf.length() - 2, strCpf.length());  
        //Concatenando o primeiro resto com o segundo.  
        nDigResult = String.valueOf(digito1) + String.valueOf(digito2);  
        //comparar o digito verificador do cpf com o primeiro resto + o segundo resto.  
        return nDigVerific.equals(nDigResult);  
    }

	private boolean verificarMesmoNumero(String strCpf) {
		
		char letra = strCpf.charAt(0);
		
		for (int i = 1; i < strCpf.length(); i++) {
			if(letra != strCpf.charAt(i)){
				return false;
			}
		}
		
		return true;
	}  

}
