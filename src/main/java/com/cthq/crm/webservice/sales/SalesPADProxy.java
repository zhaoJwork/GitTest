package com.cthq.crm.webservice.sales;

public class SalesPADProxy implements com.cthq.crm.webservice.sales.SalesPAD {
  private String _endpoint = null;
  private com.cthq.crm.webservice.sales.SalesPAD salesPAD = null;
  
  public SalesPADProxy() {
    _initSalesPADProxy();
  }
  
  public SalesPADProxy(String endpoint) {
    _endpoint = endpoint;
    _initSalesPADProxy();
  }
  
  private void _initSalesPADProxy() {
    try {
      salesPAD = (new com.cthq.crm.webservice.sales.SalesPADServiceLocator()).getSalesPAD();
      if (salesPAD != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)salesPAD)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)salesPAD)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (salesPAD != null)
      ((javax.xml.rpc.Stub)salesPAD)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.cthq.crm.webservice.sales.SalesPAD getSalesPAD() {
    if (salesPAD == null)
      _initSalesPADProxy();
    return salesPAD;
  }
  
  public java.lang.String salesPADService(java.lang.String reqXml) throws java.rmi.RemoteException{
    if (salesPAD == null)
      _initSalesPADProxy();
    return salesPAD.salesPADService(reqXml);
  }
  
  
}