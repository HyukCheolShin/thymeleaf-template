package kr.co.aia.dmd.ipro.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.aia.dmd.common.dto.PageRequestDto;
import kr.co.aia.dmd.ipro.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 화면 이동을 담당하는 컨트롤러
 */
@Slf4j
@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("")
    public String list(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "") String searchType,
            Model model) {

        var response = userService
                .getAllUsers(new PageRequestDto(page, size, keyword, searchType));
        model.addAttribute("data", response);
        model.addAttribute("searchType", searchType);
        model.addAttribute("keyword", keyword);

        return "user/list";
    }

    @GetMapping("/form")
    public String form(@RequestParam(required = false) Long id, Model model) {
        if (id != null) {
            model.addAttribute("id", id);
        }
        return "user/form";
    }

}
