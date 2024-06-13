package br.com.mcm.apimcmfood.infrastructure.serviceImpl.report;

import br.com.mcm.apimcmfood.domain.filtros.FiltroVendaDiaria;
import br.com.mcm.apimcmfood.domain.service.VendaReportService;
import br.com.mcm.apimcmfood.domain.service.VendasQueryService;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Locale;
@Service
public class PdfVendaReportService implements VendaReportService {

    private VendasQueryService vendasQueryService;

    public PdfVendaReportService(VendasQueryService vendasQueryService) {
        this.vendasQueryService = vendasQueryService;
    }

    @Override
    public byte[] emitirVendasDiarias(FiltroVendaDiaria filtro, String timeOffset) {
        try {
            var inputStream = this.getClass().getResourceAsStream(
                    "/relatorios/vendas-diarias.jasper");
            var parametros = new HashMap<String, Object>();
            parametros.put("REPORT_LOCALE", new Locale("pt", "BR"));

            var vendasDiarias = vendasQueryService.consultarVendasDiarias(filtro, timeOffset);
            var dataSource = new JRBeanCollectionDataSource(vendasDiarias);
            var jasperPrint = JasperFillManager.fillReport(inputStream, parametros, dataSource);

            return JasperExportManager.exportReportToPdf(jasperPrint);
        } catch (Exception e) {
            throw new ReportException("Não foi possível emitir relatório de vendas diárias", e);
        }
    }
}
