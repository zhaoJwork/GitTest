/**
 * SalesPADServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.cthq.crm.webservice.sales;

public class SalesPADServiceLocator extends org.apache.axis.client.Service implements com.cthq.crm.webservice.sales.SalesPADService {

    public SalesPADServiceLocator() {
    }


    public SalesPADServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public SalesPADServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for SalesPAD
    private java.lang.String SalesPAD_address = "http://10.1.12.107:9080/CRM-PAD/services/SalesPAD";

    public java.lang.String getSalesPADAddress() {
        return SalesPAD_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String SalesPADWSDDServiceName = "SalesPAD";

    public java.lang.String getSalesPADWSDDServiceName() {
        return SalesPADWSDDServiceName;
    }

    public void setSalesPADWSDDServiceName(java.lang.String name) {
        SalesPADWSDDServiceName = name;
    }

    public com.cthq.crm.webservice.sales.SalesPAD getSalesPAD() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(SalesPAD_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getSalesPAD(endpoint);
    }

    public com.cthq.crm.webservice.sales.SalesPAD getSalesPAD(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.cthq.crm.webservice.sales.SalesPADSoapBindingStub _stub = new com.cthq.crm.webservice.sales.SalesPADSoapBindingStub(portAddress, this);
            _stub.setPortName(getSalesPADWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setSalesPADEndpointAddress(java.lang.String address) {
        SalesPAD_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.cthq.crm.webservice.sales.SalesPAD.class.isAssignableFrom(serviceEndpointInterface)) {
                com.cthq.crm.webservice.sales.SalesPADSoapBindingStub _stub = new com.cthq.crm.webservice.sales.SalesPADSoapBindingStub(new java.net.URL(SalesPAD_address), this);
                _stub.setPortName(getSalesPADWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("SalesPAD".equals(inputPortName)) {
            return getSalesPAD();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://sales.webservice.crm.cthq.com", "SalesPADService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://sales.webservice.crm.cthq.com", "SalesPAD"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("SalesPAD".equals(portName)) {
            setSalesPADEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
