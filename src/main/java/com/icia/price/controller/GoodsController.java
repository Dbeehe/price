package com.icia.price.controller;


import com.icia.price.dto.BoardDTO;
import com.icia.price.dto.GoodsDTO;
import com.icia.price.service.GoodsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

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
                          @RequestParam(value = "type", required = false, defaultValue = "boardTitle") String type,
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

}
