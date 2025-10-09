package dev.ohhoonim.user.infra;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import dev.ohhoonim.user.model.ChangeDetail;
import dev.ohhoonim.user.model.PendingChange;

@Mapper
public interface PendingMapper {

    void addPending(
            PendingChange pending);

    List<PendingChange> pendings(
            @Param("effectiveDate") LocalDateTime effectiveDate);

    void addChangeDetail(
            ChangeDetail changeDetail);


}
