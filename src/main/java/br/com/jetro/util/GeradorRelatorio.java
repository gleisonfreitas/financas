package br.com.jetro.util;

import java.util.Collection;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class GeradorRelatorio {
	
	
	public static void gerarRelatorio(String relatorio, String nomeRelatorio, Map<String, Object> param, Collection dados){
		
		try {
			
			FacesContext context = FacesContext.getCurrentInstance();
			
			HttpServletResponse response = ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse());
			response.setContentType("application/pdf");    
            response.addHeader("Content-disposition", "attachment; filename=\""+nomeRelatorio+".pdf\"");    
			
            JasperPrint print = JasperFillManager.fillReport(relatorio, param, new JRBeanCollectionDataSource(dados));
            
            JasperExportManager.exportReportToPdfStream(print, response.getOutputStream());                        
            context.getApplication().getStateManager().saveView(context);    
            context.renderResponse();
            context.responseComplete();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
