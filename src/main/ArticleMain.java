package main;

import db.DBConn;
import repository.ArticleRepository;
import repository.CommentRepository; // 추가
import service.ArticleService;
import service.CommentService; // 추가
import view.ArticleView;

import java.sql.Connection;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ArticleMain {
    public static void main(String[] args) {
        Connection connection = DBConn.getConnection();
        if (connection == null) {
            System.out.println("DB 연결에 실패했습니다. 프로그램을 종료합니다.");
            return;
        }

        Scanner sc = new Scanner(System.in);

        ArticleRepository articleRepository = new ArticleRepository(connection);
        ArticleService articleService = new ArticleService(articleRepository);

        CommentRepository commentRepository = new CommentRepository(connection);
        CommentService commentService = new CommentService(commentRepository);

        ArticleView articleView = new ArticleView(sc, articleService, commentService);

        int input = -1;

        while (true) {
            do {
                System.out.println("0. 새글 1.전체보기 2.자세히보기 3.게시글삭제 4.수정 5.종료");
                System.out.print("번호 입력 : ");

                try {
                    input = sc.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("잘못된 입력입니다. '숫자'만 입력해 주세요");
                    sc.nextLine();
                    input = -1;
                }

                if (input < 0 || input > 5) {
                    System.out.println("없는 번호입니다. 0부터 5 사이의 숫자가 아닙니다.");
                }

            } while (input < 0 || input > 5);

            try {
                switch (input) {
                    case 0: articleView.insert(); break;
                    case 1: articleView.showAll(); break;
                    case 2: articleView.detail(); break; // 🌟 이 안에서 댓글 로직이 돌아가게 됩니다.
                    case 3: articleView.delete(); break;
                    case 4: articleView.update(); break;
                    case 5:
                        System.out.println("프로그램을 종료합니다.");
                        DBConn.close();
                        sc.close();
                        return;
                }
            } catch (Exception e) {
                System.out.println("🚨 기능 실행 중 오류 발생 : " + e.getMessage());
            }
        }
    }
}