package UI;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import BLL.AutoFunction;
import Model.RankCell;
public class ShowPersonalRankForm extends JFrame implements ActionListener{
	private JPanel contentPane;
	private JButton returnBtn;
	private JTable table;
	private AdminClient parent;
	public ShowPersonalRankForm(AdminClient parent) {
		this.parent = parent;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 562, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		this.add(contentPane);
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.CENTER);
		
		String[] headers = { "����", "����", "�÷�" };
		RankCell[] rankCells = null;
		try {
			rankCells = AutoFunction.getRankCellofAllAthlete();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		Object[][] cellData = new String[rankCells.length][3];
		DefaultTableModel model = new DefaultTableModel(cellData, headers) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		table = new JTable(model);
		table.getTableHeader().setReorderingAllowed(false);//��ͷ�������ҽ���
		table.getTableHeader().setResizingAllowed(false);//��ͷ��С���ɸı�
		
		JScrollPane jsp = new JScrollPane(table);
		panel_1.add(jsp);
		JPanel retPanel = new JPanel();
		contentPane.add(retPanel, BorderLayout.SOUTH);
		returnBtn = new JButton("����");
		retPanel.add(returnBtn);	
		returnBtn.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == returnBtn) {
			if(parent != null) {
				parent.setVisible(true);
			}
			this.dispose();
		}
	}
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ShowPersonalRankForm frame = new ShowPersonalRankForm(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
