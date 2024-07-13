        package com.example.Crewpr.service;

        import com.example.Crewpr.entity.Vacation;
        import com.example.Crewpr.entity.VacationHistory;
        import java.time.DayOfWeek;
        import java.time.LocalDate;
        import java.util.List;


        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Service;
        import com.example.Crewpr.repository.VacationRepository;


        @Service
        public class VacationServiceImp implements VacationService{


        @Autowired
        private VacationRepository vacationRepository;


            @Override
            public Long RemainingDays(Long employeeId) {

                return vacationRepository.getRemainingDays(employeeId);
            }


            @Override
            public Long TotalVacationDays(Long employeeId) {
                Long TotalVacationbalance=30L-RemainingDays(employeeId);

                return TotalVacationbalance;
            }




            public List<VacationHistory> findVacationHistoryByEmployeeId(Long employeeId) {
            return vacationRepository.findVacationHistoryByEmployeeId(employeeId);
        }


            @Override
            public Vacation submitVacation(Vacation vacation) {
            Long countVacation = countVacationDays(vacation.getStartDate(),vacation.getEndDate());
            Long EmployeeId = vacation.getEmployee().getId();
            Long totalVacationDays= vacationRepository.VacationDays(EmployeeId);
            Long remainingVacationDays= totalVacationDays - countVacation;

            if(isVacationValidForSubmission(vacation)){
                vacationRepository.updateRemainingDays(EmployeeId, remainingVacationDays);
                vacationRepository.UpdateVacationDays(EmployeeId,remainingVacationDays);
                return vacationRepository.save(vacation);
            }
            return null;

        }

        public Long getTotalRemainingDays(Long employeeId) {
            return vacationRepository.getRemainingDays(employeeId);
        }



      



        public long countVacationDays(LocalDate startDate, LocalDate endDate) {
            long count = 0;
            LocalDate currentDate = startDate;
            while (!currentDate.isAfter(endDate)) {
                DayOfWeek dayOfWeek = currentDate.getDayOfWeek();
                if (dayOfWeek != DayOfWeek.FRIDAY && dayOfWeek != DayOfWeek.SATURDAY) {
                    count++;
                }
                currentDate = currentDate.plusDays(1);
            }
            return count;
        }




        public boolean isVacationValid(Vacation vacation) {
            if (vacation.getStartDate().isAfter(vacation.getEndDate())) {
                return false;
            }
            if (vacation.getStartDate().isBefore(LocalDate.now())) {
                return false;
            }
            if (vacation.getStartDate().getDayOfWeek() == DayOfWeek.FRIDAY || vacation.getStartDate().getDayOfWeek() == DayOfWeek.SATURDAY) {
                return false;
            }
            if (vacation.getEndDate().getDayOfWeek() == DayOfWeek.FRIDAY || vacation.getEndDate().getDayOfWeek() == DayOfWeek.SATURDAY) {
                return false;
            }
            return true;
        }



        public boolean isVacationBalanceEnough(Vacation vacation) {
            Long totalVacationBalance = getTotalRemainingDays(vacation.getEmployee().getId());
            long vacationDays = countVacationDays(vacation.getStartDate(), vacation.getEndDate());
            return totalVacationBalance >= vacationDays;
        }
        public boolean isVacationOverlapping(Vacation vacation) {
            List<VacationHistory> vacationHistory = findVacationHistoryByEmployeeId(vacation.getEmployee().getId());
            for (VacationHistory v : vacationHistory) {
                if (v.getStartDate().isBefore(vacation.getEndDate()) && v.getEndDate().isAfter(vacation.getStartDate())) {
                    return true;
                }
            }
            return false;
        }



        public boolean isVacationValidForSubmission(Vacation vacation) {
            return isVacationValid(vacation) && isVacationBalanceEnough(vacation) && !isVacationOverlapping(vacation);

        }



        }