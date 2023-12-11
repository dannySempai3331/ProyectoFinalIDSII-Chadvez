@WebServlet("/darDeBajaEquipo")
public class DarDeBajaEquipoServlet extends HttpServlet {

    private JugadoresEquipoDao jugadoresEquipoDao;

    @Override
    public void init() throws ServletException {
        // Inicializar la conexión y otras dependencias
        jugadoresEquipoDao = new JugadoresEquipoImp();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtener parámetro de la solicitud (ID del equipo)
        int idEquipo = Integer.parseInt(request.getParameter("idEquipo"));

        // Llamar al método de dar de baja a un equipo
        jugadoresEquipoDao.darDeBajaEquipo(idEquipo);

        // Puedes redirigir a una página de éxito o realizar otras acciones según tu lógica
        response.sendRedirect("/pagina-de-exito.jsp");
    }
}