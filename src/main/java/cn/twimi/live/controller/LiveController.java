package cn.twimi.live.controller;

import cn.twimi.common.annotation.Permission;
import cn.twimi.util.ApiResponse;
import cn.twimi.util.PageData;
import cn.twimi.live.model.Live;
import cn.twimi.live.model.Message;
import cn.twimi.common.model.User;
import cn.twimi.live.service.LiveService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/live")
@CrossOrigin
public class LiveController {

    private final LiveService liveService;

    public LiveController(LiveService liveService) {
        this.liveService = liveService;
    }

    @Permission({User.HOST, User.GROUP})
    @PostMapping("/create")
    public ApiResponse<Live> createLive(
            HttpServletRequest request,
            @RequestParam("title") String title
    ) {
        User user = (User) request.getAttribute("curUser");
        return liveService.create(new Live(user.getId(), title));
    }

    @Permission({User.HOST, User.GROUP})
    @PostMapping("/update")
    public ApiResponse<Message> updateLive(
            HttpServletRequest request,
            @RequestParam("liveId") String liveId,
            @RequestParam("extra") String extra
    ) {
        User user = (User) request.getAttribute("curUser");
        return liveService.updateExtra(user.getId(), liveId, extra);
    }

    @PostMapping("/list")
    public ApiResponse<PageData<Live>> apiList(
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size) {
        List<Live> lives = liveService.listByPage(page, size);
        PageData<Live> livePageData = PageData.<Live>builder()
                .list(lives).page(page)
                .total(lives.size())
                .build();
        return ApiResponse.<PageData<Live>>builder().status(0).msg("ok").data(livePageData).build();
    }

    @PostMapping("/get/{liveId}")
    public ApiResponse<Live> getLive(
            @PathVariable String liveId
    ) {
        return liveService.get(liveId);
    }

    @Permission({User.HOST, User.GROUP})
    @PostMapping("/stop")
    public ApiResponse<Message> stopLive(
            HttpServletRequest request,
            @RequestParam("liveId") String liveId
    ) {
        User user = (User) request.getAttribute("curUser");
        return liveService.stop(user.getId(), liveId);
    }

    @Permission({User.HOST, User.GROUP})
    @PostMapping("/mine")
    public ApiResponse<PageData<Live>> apiList(
            HttpServletRequest request,
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size) {
        User user = (User) request.getAttribute("curUser");
        List<Live> lives = liveService.listByUserId(user.getId(), page, size);
        PageData<Live> livePageData = PageData.<Live>builder()
                .list(lives).page(page)
                .total(lives.size())
                .build();
        return ApiResponse.<PageData<Live>>builder().status(0).msg("ok").data(livePageData).build();
    }

    @PostMapping("/search")
    public ApiResponse<PageData<Live>> apiSearch(
            @RequestParam(name = "keyword") String keyword,
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size) {
        List<Live> lives = liveService.listBySearch(keyword, page, size);
        PageData<Live> livePageData = PageData.<Live>builder()
                .list(lives).page(page)
                .total(lives.size())
                .build();
        return ApiResponse.<PageData<Live>>builder().status(0).msg("ok").data(livePageData).build();
    }

    @Permission({User.HOST, User.GROUP})
    @PostMapping("/push/{liveId}")
    public ApiResponse<Message> push(
            HttpServletRequest request,
            @PathVariable String liveId,
            @RequestParam(value = "content", required = false, defaultValue = "") String content,
            @RequestParam(value = "file", required = false) MultipartFile file) {
        User user = (User) request.getAttribute("curUser");
        return liveService.push(user.getId(), liveId, content, file);
    }

    @PostMapping("/list/{liveId}")
    public ApiResponse<PageData<Message>> apiListHistory(
            @PathVariable String liveId,
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size) {
        List<Message> lives = liveService.listHistory(liveId, page, size);
        PageData<Message> messagePageData = PageData.<Message>builder()
                .list(lives).page(page)
                .total(lives.size())
                .build();
        return ApiResponse.<PageData<Message>>builder().status(0).msg("ok").data(messagePageData).build();
    }
}
