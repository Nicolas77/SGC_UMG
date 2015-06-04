package com.jn.sgcumg.models;


import android.os.Parcel;
import android.os.Parcelable;

import com.jn.sgcumg.utils.ClienteItemInterface;


public class Cliente implements Parcelable,ClienteItemInterface {

    private String idCliente;
    private String direcCliente;
    private String dpi;
    private String edad;
    private String nombreDelCliente;
    private String sexo;
    private String telCasa;
    private String telNegocio;


    public Cliente(){;}



    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public String getDirecCliente() {
        return direcCliente;
    }

    public void setDirecCliente(String direcCliente) {
        this.direcCliente = direcCliente;
    }

    public String getDpi() {
        return dpi;
    }

    public void setDpi(String dpi) {
        this.dpi = dpi;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getNombreDelCliente() {
        return nombreDelCliente;
    }

    public void setNombreDelCliente(String nombreDelCliente) {
        this.nombreDelCliente = nombreDelCliente;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getTelCasa() {
        return telCasa;
    }

    public void setTelCasa(String telCasa) {
        this.telCasa = telCasa;
    }

    public String getTelNegocio() {
        return telNegocio;
    }

    public void setTelNegocio(String telNegocio) {
        this.telNegocio = telNegocio;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public Cliente(Parcel in){
        this.idCliente=in.readString();
        this.direcCliente=in.readString();
        this.dpi=in.readString();
        this.edad=in.readString();
        this.nombreDelCliente=in.readString();
        this.sexo=in.readString();
        this.telCasa=in.readString();
        this.telNegocio=in.readString();

    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeString(idCliente);
        dest.writeString(direcCliente);
        dest.writeString(dpi);
        dest.writeString(edad);
        dest.writeString(nombreDelCliente);
        dest.writeString(sexo);
        dest.writeString(telCasa);
        dest.writeString(telNegocio);

    }

    @Override
    public String getItemForIndex() {
        return nombreDelCliente;
    }
}
