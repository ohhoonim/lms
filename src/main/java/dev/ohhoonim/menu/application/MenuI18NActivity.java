package dev.ohhoonim.menu.application;

import java.util.List;
import dev.ohhoonim.component.auditing.model.Id;
import dev.ohhoonim.menu.model.LanguageCode;
import dev.ohhoonim.menu.model.MenuText;

// UC-MENU-02
public interface MenuI18NActivity {

    List<MenuText> menuTextsByMenuItem(Id menuItem);

    MenuText getMenuText(Id menuItem, LanguageCode languageCode);

    void addMenuText(MenuText newMenuText);

    void modifyMenuText(MenuText menuText);

    void removeMenuText(Id menuTextId);

}

//////////////////////////////////////////////////////////////
/// 
/// 02: 메뉴 다국어 설정 및 관리
/// 
/// //////////////////////////////////////////////////////////////
/*
@startuml UC02_Menu_I18N_Activity
title UC-02: 메뉴 다국어 설정 (I18N) 액티비티 다이어그램

start
:메뉴 관리자, 다국어 설정 요청 (특정 Menu_ID);
:요청된 Menu_ID의 TextContentId 조회;

if (TextContentId 존재?) then (No)
    :TBL_TEXT_CONTENT에 원본 텍스트 항목 생성;
    :새로운 TextContentId를 TBL_MENU_ITEM에 연결;
else (Yes)
endif

:대상 언어 (예: ko, en, jp) 선택;
:Translated_Title 및 Translated_Description 입력;

if (해당 언어의 기존 번역 존재?) then (Yes)
    :TBL_TRANSLATION 테이블에서 기존 번역본 업데이트;
else (No)
    :TBL_TRANSLATION 테이블에 새 번역 항목 삽입 (TextContentId, Language_ID, Translated_Title);
endif

:다국어 변경 사항 Commit;

partition "Audit Log" {
    :다국어 변경 감사 로그 기록;
}

stop
@enduml

*/
