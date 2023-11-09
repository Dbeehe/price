package com.icia.price.controller;


import com.icia.price.dto.BoardDTO;
import com.icia.price.dto.CommentDTO;
import com.icia.price.dto.GoodsDTO;
import com.icia.price.dto.MemberDTO;
import com.icia.price.service.GoodsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/goods")
@RequiredArgsConstructor
public class GoodsController {

    private final GoodsService goodsService;

    @GetMapping("/save")
    public String save() {
        return "goodsPages/goodsSave";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute GoodsDTO goodsDTO) throws IOException {
        goodsService.save(goodsDTO);
        return "goodsPages/goodsList";
    }

    @GetMapping
    public String findAll(Model model,
                          @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                          @RequestParam(value = "type", required = false, defaultValue = "fileName") String type,
                          @RequestParam(value = "q", required = false, defaultValue = "") String q) {
        Page<GoodsDTO> goodsDTOList = goodsService.findAll(page, type, q);

        int blockLimit = 3;
        int startPage = (((int) (Math.ceil((double) page / blockLimit))) - 1) * blockLimit + 1;
        int endPage = ((startPage + blockLimit - 1) < goodsDTOList.getTotalPages()) ? startPage + blockLimit - 1 : goodsDTOList.getTotalPages();

        model.addAttribute("goodsList", goodsDTOList);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("page", page);
        model.addAttribute("type", type);
        model.addAttribute("q", q);

        return "goodsPages/goodsList";
    }


    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Long id, Model model,
                           @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                           @RequestParam(value = "type", required = false, defaultValue = "fileName") String type,
                           @RequestParam(value = "q", required = false, defaultValue = "") String q) {
        model.addAttribute("page", page);
        model.addAttribute("type", type);
        model.addAttribute("q", q);
        try {
            GoodsDTO goodsDTO = goodsService.findById(id);
            model.addAttribute("goods", goodsDTO);
            return "goodsPages/goodsDetail";
        } catch (NoSuchElementException e) {
            return "goodsPages/NotFound";
        }
    }
}
