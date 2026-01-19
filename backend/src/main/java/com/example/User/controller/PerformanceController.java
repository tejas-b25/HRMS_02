package com.example.User.controller;

import com.example.User.dto.FeedbackThreeSixtyDTO;
import com.example.User.dto.KpiMasterDto;
import com.example.User.dto.PerformanceDashboardSummaryDTO;
import com.example.User.dto.PerformanceReviewDto;
import com.example.User.service.FeedbackThreeSixtyService;
import com.example.User.service.KpiService;
import com.example.User.service.PerformanceDashboardSummaryService;
import com.example.User.service.PerformanceReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/performance")
@RequiredArgsConstructor
public class PerformanceController {
    @Autowired
    private  KpiService kpiService;
    @Autowired
    private  PerformanceReviewService reviewService;
    @Autowired
    private FeedbackThreeSixtyService service;
    @Autowired
    private PerformanceDashboardSummaryService dashboardSummary;

    @PostMapping("/create")
    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity<KpiMasterDto> createKpi(@RequestBody KpiMasterDto dto) {
        return ResponseEntity.ok(kpiService.createKpi(dto));
    }
    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity<KpiMasterDto> updateKpi(@PathVariable Long id, @RequestBody KpiMasterDto dto) {
        return ResponseEntity.ok(kpiService.updateKpi(id, dto));
    }
    @GetMapping("/kpi/{id}")
    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity<KpiMasterDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(kpiService.getKpiById(id));
    }
    @GetMapping("/allkpi")
    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity<List<KpiMasterDto>> getAll() {
        return ResponseEntity.ok(kpiService.getAllKpi());
    }
    @DeleteMapping("/kpi/{id}")
    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity<String> deleteKpi(@PathVariable Long id) {
        kpiService.deleteKpi(id);
        return ResponseEntity.ok("KPI deleted successfully with id: " + id);
    }



    //------------Porfermonce Review -----------
    @PostMapping("/createReview")
    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity<PerformanceReviewDto> createReview(@RequestBody PerformanceReviewDto dto) {
        return ResponseEntity.ok(reviewService.createReview(dto));
    }
    @PutMapping("/updateReview/{reviewId}")
    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity<PerformanceReviewDto> updateReview(@PathVariable Long reviewId,
                                                             @RequestBody PerformanceReviewDto dto) {
        return ResponseEntity.ok(reviewService.updateReview(reviewId, dto));
    }
    @GetMapping("/getReview/{reviewId}")
    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity<PerformanceReviewDto> getReview(@PathVariable Long reviewId) {
        return ResponseEntity.ok(reviewService.getReviewById(reviewId));
    }

    @GetMapping("/employee/{employeeId}")
    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity<List<PerformanceReviewDto>> getEmployeeReviews(@PathVariable Long employeeId) {
        return ResponseEntity.ok(reviewService.getReviewsByEmployee(employeeId));
    }
    @DeleteMapping("/Review/{reviewId}")
    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity<Void> deleteReview(@PathVariable Long reviewId) {
        reviewService.deleteReview(reviewId);
        return ResponseEntity.noContent().build();
    }
    //-----------------------feedback Api-----------------------------------

    @PostMapping("/feedback/create")
    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity<FeedbackThreeSixtyDTO> createFeedback(@Valid @RequestBody FeedbackThreeSixtyDTO dto) {
        return ResponseEntity.ok(service.createFeedback(dto));
    }
    @PutMapping("/feedback/update/{id}")
    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity<FeedbackThreeSixtyDTO> updateFeedback(
            @PathVariable Long id,
            @Valid @RequestBody FeedbackThreeSixtyDTO dto) {
        return ResponseEntity.ok(service.updateFeedback(id, dto));
    }
    @GetMapping("/getAllFeedback")
    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity<List<FeedbackThreeSixtyDTO>> getAllFeedbacks() {
        return ResponseEntity.ok(service.getAllFeedbacks());
    }
    @GetMapping("/getFeedback/{id}")
    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity<FeedbackThreeSixtyDTO> getFeedbackById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getFeedbackById(id));
    }
    @GetMapping("/Feedbackreviewee/{revieweeId}")
    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity<List<FeedbackThreeSixtyDTO>> getFeedbacksByReviewee(@PathVariable Long revieweeId) {
        return ResponseEntity.ok(service.getFeedbacksByReviewee(revieweeId));
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity<String> deleteFeedback(@PathVariable Long id) {
        service.deleteFeedback(id);
        return ResponseEntity.ok("Feedback deleted successfully");
    }

    //----------------------------  Performance Dashboard---------------------

    @PostMapping("/dashboard")
    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity<PerformanceDashboardSummaryDTO> createDashboard(@RequestBody PerformanceDashboardSummaryDTO dto) {
        return ResponseEntity.ok(dashboardSummary.createDashboardSummary(dto));
    }
    @GetMapping("/dashboard/{id}")
    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity<PerformanceDashboardSummaryDTO> getDashboardById(@PathVariable Long id) {
        return ResponseEntity.ok(dashboardSummary.getDashboardById(id));
    }
    @GetMapping("/gellAllDashboard")
    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity<List<PerformanceDashboardSummaryDTO>> getAllDashboards() {
        return ResponseEntity.ok(dashboardSummary.getAllDashboards());
    }
    @GetMapping("/department/{departmentName}")
    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity<List<PerformanceDashboardSummaryDTO>> getByDepartment(@PathVariable String departmentName) {
        return ResponseEntity.ok(dashboardSummary.getDashboardByDepartment(departmentName));
    }
    @GetMapping("/period/{reviewPeriod}")
    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity<List<PerformanceDashboardSummaryDTO>> getByPeriod(@PathVariable String reviewPeriod) {
        return ResponseEntity.ok(dashboardSummary.getDashboardByReviewPeriod(reviewPeriod));
    }
    @DeleteMapping("/dashboard/{id}")
    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity<String> deleteDashboard(@PathVariable Long id) {
        dashboardSummary.deleteDashboard(id);
        return ResponseEntity.ok("Dashboard deleted successfully with ID: " + id);
    }
}
