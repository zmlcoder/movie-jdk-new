package com.mingliz.movie.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import com.mingliz.movie.model.Movie;
import com.mingliz.movie.model.service.IMovieService;

public class MovieMainFrame extends JFrame {

   public MovieMainFrame() {
      initUI();
   }

   private void initUI() {
      var contentPanel = new JPanel(new BorderLayout());
      var textField = new JTextField();
      var searchButton = new JButton("Search");
      var moviePanel = new JPanel(new FlowLayout());
      var scrollPane = new JScrollPane(moviePanel);
      var service = IMovieService.createInstance();

      searchButton.addActionListener(e -> {
         var searchText = textField.getText();

         if ("".equals(searchText.trim())) {
            return;
         }

         new SwingWorker<List<Movie>, Void>() {

            @Override
            protected List<Movie> doInBackground() throws Exception {
               return service.search(searchText);
            }

            @Override
            protected void done() {
               try {
                  moviePanel.removeAll();
                  for (var movie : get()) {
                     JButton button = new JButton(movie.getName());
                     loadIcon(button, movie);
                     moviePanel.add(button);
                  }
                  moviePanel.revalidate();
                  moviePanel.repaint();
               } catch (InterruptedException | ExecutionException e) {
                  e.printStackTrace();
               }
            }

         }.execute();

      });

      contentPanel.add(textField, BorderLayout.NORTH);
      contentPanel.add(searchButton, BorderLayout.EAST);
      contentPanel.add(scrollPane, BorderLayout.CENTER);

      setContentPane(contentPanel);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setBounds(100, 100, 800, 400);
      setVisible(true);
   }

   private void loadIcon(JButton button, Movie movie) {
      new SwingWorker<ImageIcon, Void>() {

         @Override
         protected ImageIcon doInBackground() throws Exception {
            return new ImageIcon(new URL(movie.getImage()));
         }

         protected void done() {
            try {
               button.setIcon(get());
            } catch (InterruptedException | ExecutionException e) {
               e.printStackTrace();
            }
         };

      }.execute();
   }

   public static void main(String[] args) {

      SwingUtilities.invokeLater(new Runnable() {
         @Override
         public void run() {
            new MovieMainFrame();
         }
      });

   }

}
