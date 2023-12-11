@WebServlet("/inscribirJugadorEnEquipo")
public class InscribirJugadorEnEquipoServlet extends HttpServlet {

    private JugadoresEquipoDao jugadoresEquipoDao;

    @Override
    public void init() throws ServletException {
        // Inicializar la conexión y otras dependencias
        jugadoresEquipoDao = new JugadoresEquipoImp();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtener parámetros de la solicitud (ID del jugador y del equipo)
        int idJugador = Integer.parseInt(request.getParameter("idJugador"));
        int idEquipo = Integer.parseInt(request.getParameter("idEquipo"));

        // Crear instancia del servicio de equipos
        EquipoService equipoService = new EquipoService(jugadoresEquipoDao);

        // Llamar al método de inscripción de jugador en equipo
        equipoService.inscribirJugadorEnEquipo(idJugador, idEquipo);

        // Puedes redirigir a una página de éxito o realizar otras acciones según tu lógica
        response.sendRedirect("/pagina-de-exito.jsp");
    }
}