package de.lamp.cryptopanel.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "invoice_payments")
public class Invoices_payments implements Serializable {

    @Id
    @Column(name = "payment_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private Integer invoice_id;
    private String uuid;
    private String currency;
    private double electrum_amount;
    private String electrum_id;
    private String electrum_uri;
    private String electrum_address;
    private String electrum_expires_at;
    private String created_at;
    private String update_at;

    @ManyToOne(targetEntity = Invoices.class)
    private Invoices invoices;

    public Invoices_payments(){

    }

    public Invoices_payments(int id, Integer invoice_id, String uuid, String currency, double electrum_amount,
                             String electrum_id, String electrum_uri, String electrum_address,
                             String electrum_expires_at, String created_at, String update_at) {
        this.id = id;
        this.invoice_id = invoice_id;
        this.uuid = uuid;
        this.currency = currency;
        this.electrum_amount = electrum_amount;
        this.electrum_id = electrum_id;
        this.electrum_uri = electrum_uri;
        this.electrum_address = electrum_address;
        this.electrum_expires_at = electrum_expires_at;
        this.created_at = created_at;
        this.update_at = update_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getInvoice_id() {
        return invoice_id;
    }

    public void setInvoice_id(Integer invoice_id) {
        this.invoice_id = invoice_id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public double getElectrum_amount() {
        return electrum_amount;
    }

    public void setElectrum_amount(double electrum_amount) {
        this.electrum_amount = electrum_amount;
    }

    public String getElectrum_id() {
        return electrum_id;
    }

    public void setElectrum_id(String electrum_id) {
        this.electrum_id = electrum_id;
    }

    public String getElectrum_uri() {
        return electrum_uri;
    }

    public void setElectrum_uri(String electrum_uri) {
        this.electrum_uri = electrum_uri;
    }

    public String getElectrum_address() {
        return electrum_address;
    }

    public void setElectrum_address(String electrum_address) {
        this.electrum_address = electrum_address;
    }

    public String getElectrum_expires_at() {
        return electrum_expires_at;
    }

    public void setElectrum_expires_at(String electrum_expires_at) {
        this.electrum_expires_at = electrum_expires_at;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdate_at() {
        return update_at;
    }

    public void setUpdate_at(String update_at) {
        this.update_at = update_at;
    }

    public Invoices getInvoices() {
        return invoices;
    }

    public void setInvoices(Invoices invoices) {
        this.invoices = invoices;
    }

    @Override
    public String toString() {
        return "Invoices_payments{" +
                "id=" + id +
                ", invoice_id=" + invoice_id +
                ", uuid='" + uuid + '\'' +
                ", currency='" + currency + '\'' +
                ", electrum_amount=" + electrum_amount +
                ", electrum_id='" + electrum_id + '\'' +
                ", electrum_uri='" + electrum_uri + '\'' +
                ", electrum_address='" + electrum_address + '\'' +
                ", electrum_expires_at='" + electrum_expires_at + '\'' +
                ", created_at='" + created_at + '\'' +
                ", update_at='" + update_at + '\'' +
                '}';
    }
}