package dtos;

public class GruposTorneos {

    private int idGrupo;
    private int idTorneo;

    public GruposTorneos() {
    }

    public int getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(int idGrupo) {
        this.idGrupo = idGrupo;
    }

    public int getIdTorneo() {
        return idTorneo;
    }

    public void setIdTorneo(int idTorneo) {
        this.idTorneo = idTorneo;
    }

    @Override
    public String toString() {
        return "GruposTorneos{" +
                "idGrupo=" + idGrupo +
                ", idTorneo=" + idTorneo +
                '}';
    }
}
