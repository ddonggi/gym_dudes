package com.weight.gym_dude.answer;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/*
 * Create by dglee on 2023-09-12
 */
@Setter
@Getter
public class AnswerForm {
    @NotEmpty(message = "내용은 필수 항목 입니다")
    @Size(max=650)
    private String content;
}
