import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class ProdutosDAO {

    Connection conn;
    PreparedStatement prep;
    ResultSet resultset;
    ArrayList<ProdutosDTO> listagem = new ArrayList<>();

    public void cadastrarProduto(ProdutosDTO produto) {

        String sql = "INSERT INTO produtos(nome, valor, status) VALUES (?, ?, ?)";

        conn = new conectaDAO().connectDB();

        try {

            prep = conn.prepareStatement(sql);

            prep.setString(1, produto.getNome());
            prep.setInt(2, produto.getValor());
            prep.setString(3, produto.getStatus());

            prep.execute();

            JOptionPane.showMessageDialog(null, "Produto cadastrado!");

        } catch (HeadlessException | SQLException e) {

            JOptionPane.showMessageDialog(null,
                    "Erro ao cadastrar: " + e.getMessage());

        }
    }

    public ArrayList<ProdutosDTO> listarProdutos() {

        String sql = "SELECT * FROM produtos";

        conn = new conectaDAO().connectDB();

        try {

            prep = conn.prepareStatement(sql);

            resultset = prep.executeQuery();

            while (resultset.next()) {

                ProdutosDTO produto = new ProdutosDTO();

                produto.setId(resultset.getInt("id"));
                produto.setNome(resultset.getString("nome"));
                produto.setValor(resultset.getInt("valor"));
                produto.setStatus(resultset.getString("status"));

                listagem.add(produto);
            }

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(null,
                    "Erro ao listar: " + e.getMessage());

        }

        return listagem;
    }
    
    public void venderProduto(int id) {

    String sql = "UPDATE produtos SET status = 'Vendido' WHERE id = ?";

    Connection conn = null;
    PreparedStatement pstm = null;

    try {

        conn = new conectaDAO().connectDB();

        pstm = conn.prepareStatement(sql);

        pstm.setInt(1, id);

        pstm.executeUpdate();

    } catch (SQLException e) { 
        JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage());
    } finally {

        try {

            if (pstm != null) {
                pstm.close();
            }

            if (conn != null) {
                conn.close();
            }

        } catch (SQLException e) {
    JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage());
}
    }
}
    public ArrayList<ProdutosDTO> listarProdutosVendidos() {

    String sql = "SELECT * FROM produtos WHERE status = 'Vendido'";

    ArrayList<ProdutosDTO> lista = new ArrayList<>();

    Connection conn = null;
    PreparedStatement pstm = null;
    ResultSet rs = null;

    try {

        conn = new conectaDAO().connectDB();

        pstm = conn.prepareStatement(sql);

        rs = pstm.executeQuery();

        while (rs.next()) {

            ProdutosDTO obj = new ProdutosDTO();

            obj.setId(rs.getInt("id"));
            obj.setNome(rs.getString("nome"));
            obj.setValor(rs.getInt("valor"));
            obj.setStatus(rs.getString("status"));

            lista.add(obj);
        }

    } catch (SQLException e) {

        JOptionPane.showMessageDialog(null, "Erro ao listar vendidos: " + e.getMessage());

    } finally {

        try {

            if (rs != null) {
                rs.close();
            }

            if (pstm != null) {
                pstm.close();
            }

            if (conn != null) {
                conn.close();
            }

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(null, "Erro ao fechar conexão");
        }
    }

    return lista;
}
}
    


