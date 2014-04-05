/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trunk.model;

import java.io.Serializable;

/**
 *
 * @author Ycaro_2
 */
public class Pacote implements Serializable{
    private Object conteudo;
    private TipoPacote tipo;
    
    public Pacote() {
    }
   
    public Pacote(TipoPacote tipo,Object conteudo){
    this.conteudo = conteudo;
    this.tipo = tipo;
    }
    
    public Object getConteudo() {
        return conteudo;
    }

    public TipoPacote getTipo() {
        return tipo;
    }
}
