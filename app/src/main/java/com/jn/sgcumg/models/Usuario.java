package com.jn.sgcumg.models;


public class Usuario {
    private String USUARIO;
    private String EMAIL;
    private String NOMBRE;
    private String APELLIDO;
    private String PK_COD_SUCURSAL;

    public String getUSUARIO() {
        return USUARIO;
    }

    public void setUSUARIO(String USUARIO) {
        this.USUARIO = USUARIO;
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public void setEMAIL(String EMAIL) {
        this.EMAIL = EMAIL;
    }

    public String getNOMBRE() {
        return NOMBRE;
    }

    public void setNOMBRE(String NOMBRE) {
        this.NOMBRE = NOMBRE;
    }

    public String getAPELLIDO() {
        return APELLIDO;
    }

    public void setAPELLIDO(String APELLIDO) {
        this.APELLIDO = APELLIDO;
    }

    public String getPK_COD_SUCURSAL() {
        return PK_COD_SUCURSAL;
    }

    public void setPK_COD_SUCURSAL(String PK_COD_SUCURSAL) {
        this.PK_COD_SUCURSAL = PK_COD_SUCURSAL;
    }
}
