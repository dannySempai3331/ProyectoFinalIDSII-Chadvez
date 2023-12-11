@WebServlet("/darDeBajaJugador")
public class DarDeBajaJugadorServlet extends HttpServlet {

    private JugadoresEquipoDao jugadoresEquipoDao;

    @Override
    public void init() throws ServletException {
        // Inicializar la conexión y otras dependencias
        jugadoresEquipoDao = new JugadoresEquipoImp();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtener parámetro de la solicitud (ID del jugador)
        int idJugador = Integer.parseInt(request.getParameter("idJugador"));

        // Llamar al método de dar de baja a un jugador
        jugadoresEquipoDao.darDeBajaJugador(idJugador);

        // Puedes redirigir a una página de éxito o realizar otras acciones según tu lógica
        response.sendRedirect("/pagina-de-exito.jsp");
    }
}