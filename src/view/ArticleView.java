package view;

import dto.ArticleDto;
import dto.CommentDto;
import service.ArticleService;
import service.CommentService;

import java.util.ArrayList; // 누락된 import 추가
import java.util.List;
import java.util.Scanner;

public class ArticleView {

    private Scanner scanner;
    private ArticleService service;
    private CommentService commentService;

    // 생성자
    public ArticleView(Scanner scanner, ArticleService service, CommentService commentService) {
        this.scanner = scanner;
        this.service = service;
        this.commentService = commentService;
    }

    // 3. 통합된 자세히 보기 (실제 DB 게시글 + 댓글)
    public void detail() {
        System.out.print("확인할 게시글의 아이디를 입력하세요\n> ");
        int targetArticleId = scanner.nextInt();
        scanner.nextLine(); // 엔터키 비우기

        try {
            List<ArticleDto> exists = service.getArticleOne(targetArticleId);
            if (exists.isEmpty()) {
                System.out.println("해당 ID의 게시글이 없습니다.");
                return;
            }
            ArticleDto article = exists.get(0);

            // 실제 게시글 정보 출력
            System.out.println("=====================================");
            System.out.println("🚀 ID      : " + targetArticleId);
            System.out.println("🚀 Name    : " + article.getName());
            System.out.println("🚀 Title   : " + article.getTitle());
            System.out.println("🚀 Content : " + article.getContent());
            System.out.println("=====================================");

            // 댓글 리스트 출력 및 메뉴 루프
            boolean isCommentMenu = true;
            while(isCommentMenu) {
                System.out.println("\n🎶🎶  댓글 리스트  🎶🎶");

                // 댓글 조회 시 int형인 targetArticleId를 Long으로 변환하여 넘김 (Repository 타입에 맞춤)
                List<CommentDto> comments = commentService.getComments(Long.valueOf(targetArticleId));

                if (comments.isEmpty()) {
                    System.out.println("해당 게시글에는 댓글이 없습니다.");
                } else {
                    for (CommentDto c : comments) {
                        System.out.println("\t🏷️ " + c.getId() + "\t" + c.getAuthor() + "\t" + c.getContent());
                    }
                }
                System.out.println("-------------------------------------");

                System.out.println("1.댓글입력  2.댓글수정  3.댓글삭제  4.돌아가기");
                System.out.print("> ");
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch(choice) {
                    case 1:
                        System.out.print("댓글 등록자 이름: ");
                        String author = scanner.nextLine();
                        System.out.print("댓글 내용: ");
                        String content = scanner.nextLine();

                        CommentDto newComment = new CommentDto();
                        newComment.setArticleId(Long.valueOf(targetArticleId));
                        newComment.setAuthor(author);
                        newComment.setContent(content);

                        commentService.insertComment(newComment);
                        break;

                    case 2:
                        System.out.print("수정할 댓글 번호(ID): ");
                        Long updateId = scanner.nextLong();
                        scanner.nextLine();
                        System.out.print("수정할 내용: ");
                        String updateContent = scanner.nextLine();

                        CommentDto updateDto = new CommentDto();
                        updateDto.setId(updateId);
                        updateDto.setContent(updateContent);

                        commentService.updateComment(updateDto);
                        break;

                    case 3:
                        System.out.print("삭제할 댓글 번호(ID): ");
                        Long deleteId = scanner.nextLong();
                        scanner.nextLine();

                        commentService.deleteComment(deleteId);
                        break;

                    case 4:
                        System.out.println("이전 메뉴로 돌아갑니다.");
                        isCommentMenu = false;
                        break;

                    default:
                        System.out.println("잘못된 입력입니다.");
                }
            }
        } catch (Exception e) {
            System.out.println("게시글 상세 조회 중 오류 발생 : " + e.getMessage());
        }
    }



    public void delete() throws Exception {
        System.out.println("삭제할 글 : ");
        int id = scanner.nextInt();
        int result = service.delete(id);
        System.out.println(result == 1
                ? "ID : " + id + "삭제 완료!"
                : "삭제 실패 : ID를 확인하세요");
    }

    public void update() throws Exception {
        String name = "";
        String title = "";
        String content = "";

        System.out.println("수정할 글 : ");
        int id = scanner.nextInt();
        List<ArticleDto> exists = service.getArticleOne(id);
        if (exists.isEmpty()) {
            System.out.println("해당 ID가 없습니다.");
            return;
        }
        ArticleDto oldData = exists.get(0);


        System.out.println("수정 전 이름 : ");
        System.out.println(oldData.getName());
        System.out.println("수정 할 이름 : ");
        name = scanner.next();

        System.out.println("수정 전 제목 : ");
        System.out.println(oldData.getTitle());
        System.out.println("수정 할 제목 : ");
        title = scanner.next();

        System.out.println("수정 전 내용 : ");
        System.out.println(oldData.getContent());
        System.out.println("수정 할 내용 : ");
        content = scanner.next();

        oldData.setName(name);
        oldData.setTitle(title);
        oldData.setContent(content);
        service.update(oldData);
    }

    public void insert() throws Exception {
        String name = "";
        String title = "";
        String content = "";

        System.out.println("== 새 글 등록 ==");

        System.out.println("이름 : ");
        name = scanner.next();

        System.out.println("제목 : ");
        title = scanner.next();

        System.out.println("내용 : ");
        content = scanner.next();

        service.insert(name, title, content);
    }

    public void showAll() throws Exception {
        List<ArticleDto> list = new ArrayList<>();
        list = service.getArticleAll();
        if (list.isEmpty()) {
            System.out.println("글이 비어있습니다.");
            return;
        }
        for (ArticleDto dto : list) {
            System.out.println(dto);
        }
    }
}