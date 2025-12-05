package dev.ohhoonim.user.internal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Service;
import dev.ohhoonim.component.auditing.dataBy.Created;
import dev.ohhoonim.component.auditing.dataBy.Id;
import dev.ohhoonim.component.auditing.dataBy.Modified;
import dev.ohhoonim.user.application.BatchRegisterActivity;
import dev.ohhoonim.user.application.BatchUpdateActivity;
import dev.ohhoonim.user.model.ChangeDetail;
import dev.ohhoonim.user.model.PendingChange;
import dev.ohhoonim.user.model.User;
import dev.ohhoonim.user.model.UserAttribute;
import dev.ohhoonim.user.port.HrClient;
import dev.ohhoonim.user.port.PendingChangePort;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserBatchService implements BatchRegisterActivity, BatchUpdateActivity {

    private final UserService userService;
    private final PendingChangePort pendingChangePort;
    private final HrClient hrClient;

    @Override
    public int batchUpdate(List<User> users) {
        var addedCount = 0;
        for (var userInfo : users) {
            try {
                userService.modifyInfo(userInfo);
                addedCount++;
            } catch (Exception e) {
                continue;
            }
        }
        return addedCount;
    }

    /**
     * 연계시스템에서 불러온 데이터를 pendingchange, detail에 저장
     */
    @Override
    public int fetchHrSystemToPendingChange() {
        // FIXME 연동시스템 있을시 여기에 코드 추가

        // pseudo code 
        // var fetchUsers = hrClient.fetchHrUsers();
        // var convertedPendingChanges = fetchUsers.stream().map().toList()
        // addPendingChange
        // if (convertedPendingChanges.getChangeDetail() != null)
        // loop
        //      addChangeDetail(changeDetail)

        pendingChangePort.addPending(new PendingChange());

        pendingChangePort.addChangeDetail(new ChangeDetail());

        return 0;
    }

    /**
     * pending에 저장되어있던 데이터를 특정 일시(effectiveDate)에
     * User 데이터에 반영
     */
    @Override
    public int applyPendingChangesToUser(LocalDateTime effectiveDate) {
        List<PendingChange> pendings = pendingChangePort.pendings(effectiveDate);

        int count = 0;
        for (var pending : pendings) {
            try {
                if (pending.getUser().getUserId() == null) {
                    var user = pending.getUser();
                    var userAttribute = makeAttribute.apply(pending, user);
                    user.setAttributes(userAttribute);
                    userService.register(user);
                } else {
                    var user = pending.getUser();
                    var userAttribute = makeAttribute.apply(pending, user);
                    user.setAttributes(userAttribute);
                    userService.modifyInfo(user);
                }
                count++;
            } catch (Exception e) {
                continue;
            }
        }
        return count;
    }

    private BiFunction<PendingChange, User, List<UserAttribute>> makeAttribute =
            (pending, user) -> {
                return pending.getChangeDetails().stream().map(detail -> {
                    return new UserAttribute(null, user, detail.getAttributeName(),
                            detail.getNewValue(), new Created(), new Modified());
                }).toList();
            };

    /**
     * cvs, excel 로 업로드된 데이터를 이용하여 사용자 등록할 때 사용
     * 중복체크하지 않으므로 시스템 초기 사용자 설정시에만 사용하도록 주의
     */
    @Override
    public int batchRegister(List<User> users) {
        int count = 0;
        for (var user : users) {
            try {
                userService.register(user);
                count++;
            } catch (Exception e) {
                continue;
            }
        }
        return count;
    }

    @Override
    public List<User> translateCsvToUsers(InputStream csv) {
        List<User> users = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(csv))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length == 3) {
                    User user = new User(new Id());
                    user.setUsername(values[1].trim());
                    user.setName(values[2].trim());
                    user.setEmail(values[3].trim());

                    user.setAttributes(List.of(new UserAttribute("job", values[4].trim())
                    // FIXME attribute 부분 보강 할 것 
                    ));

                    users.add(user);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("파일 읽는 도중 실패하였습니다");
        }
        return users;
    }

    @Override
    public List<User> translateExcelToUsers(InputStream excel) {
        List<User> users = new ArrayList<>();
        try (Workbook workbook = WorkbookFactory.create(excel)) {

            Sheet sheet = workbook.getSheetAt(0); // 첫 번째 시트 

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null || row.getPhysicalNumberOfCells() < 5) {
                    // row.getPhysicalNumberOfCells()는 필수값 입력갯수 체크용 
                    continue;
                }

                User user = new User(new Id());
                // 셀 인덱스는 0부터 시작합니다.
                user.setUsername(getStringCellValue(row.getCell(1)).trim());
                user.setName(getStringCellValue(row.getCell(2)).trim());
                user.setEmail(getStringCellValue(row.getCell(3)).trim());

                user.setAttributes(
                        List.of(new UserAttribute("job", getStringCellValue(row.getCell(4)).trim())
                        // FIXME : 추가 속성 설정 
                        ));

                users.add(user);
            }
        } catch (IOException e) {
            throw new RuntimeException("엑셀 파일 읽기 실패: " + e.getMessage(), e);
        }
        return users;
    }

    private String getStringCellValue(Cell cell) {
        if (cell == null) {
            return "";
        }
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return String.valueOf(cell.getNumericCellValue());
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return "";
        }
    }
}
