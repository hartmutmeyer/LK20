import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.awt.event.ActionEvent;

public class DateienErstellenUndLoeschen extends JFrame {

	private JPanel contentPane;
	private Path normaleDatei = Paths.get(getClass().getResource(".").getPath(), "NormaleDatei.txt");
	private Path versteckteDatei = Paths.get(getClass().getResource(".").getPath(), ".VersteckteDatei.txt");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DateienErstellenUndLoeschen frame = new DateienErstellenUndLoeschen();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public DateienErstellenUndLoeschen() {
		createGUI();
	}

	private void createGUI() {
		setTitle("Dateien Erstellen und Löschen");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 125);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnNormaleDateiErstellen = new JButton("Normale Datei erstellen");
		btnNormaleDateiErstellen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createNormalFile();
			}
		});
		btnNormaleDateiErstellen.setBounds(12, 18, 199, 25);
		contentPane.add(btnNormaleDateiErstellen);

		JButton btnNormaleDateiLschen = new JButton("Normale Datei löschen");
		btnNormaleDateiLschen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeNormalFile();
			}
		});
		btnNormaleDateiLschen.setBounds(12, 55, 199, 25);
		contentPane.add(btnNormaleDateiLschen);

		JButton btnVesteckteDateiErstellen = new JButton("Vesteckte Datei erstellen");
		btnVesteckteDateiErstellen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createHiddenFile();
			}
		});
		btnVesteckteDateiErstellen.setBounds(231, 18, 199, 25);
		contentPane.add(btnVesteckteDateiErstellen);

		JButton btnVersteckteDateiLschen = new JButton("Versteckte Datei löschen");
		btnVersteckteDateiLschen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeHiddenFile();
			}
		});
		btnVersteckteDateiLschen.setBounds(231, 55, 199, 25);
		contentPane.add(btnVersteckteDateiLschen);
	}

	private void createNormalFile() {
		if (Files.notExists(normaleDatei)) {
			try {
				Files.createFile(normaleDatei);
				System.out.println("Normale Datei erzeugt");
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Normale Datei existierte bereits");
		}
		try (OutputStream os = new FileOutputStream(normaleDatei.toString());
				OutputStreamWriter out = new OutputStreamWriter(os, "UTF-8")) {
			out.write("Erfolgreich in normale Datei geschrieben" + System.lineSeparator());
			out.flush();
			System.out.println("Erfolgreich in normale Datei geschrieben");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void createHiddenFile() {
		try {
			Files.createFile(versteckteDatei);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try (OutputStream os = new FileOutputStream(versteckteDatei.toString());
				OutputStreamWriter out = new OutputStreamWriter(os, "UTF-8")) {
			out.write("Versteckte Datei erfolgreich erstellt" + System.lineSeparator());
			out.flush();
			System.out.println("Versteckte Datei erfolgreich erstellt");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void removeNormalFile() {
		try {
			Files.delete(normaleDatei);
			System.out.println("Normale Datei erfolgreich gelöscht");
		} catch (NoSuchFileException x) {
			System.err.println(normaleDatei + ": no such" + " file or directory");
		} catch (DirectoryNotEmptyException x) {
			System.err.println(normaleDatei + " not empty");
		} catch (IOException e) {
			// File permission problems are caught here.
			System.err.println(e);
		}
	}

	private void removeHiddenFile() {
		try {
			Files.delete(versteckteDatei);
			System.out.println("Versteckte Datei erfolgreich gelöscht");
		} catch (NoSuchFileException e) {
			System.err.println(versteckteDatei + ": no such" + " file or directory");
		} catch (DirectoryNotEmptyException e) {
			System.err.println(versteckteDatei + " not empty");
		} catch (IOException e) {
			// File permission problems are caught here.
			System.err.println(e);
		}
	}
}
