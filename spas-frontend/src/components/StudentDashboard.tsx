import React, { useState, useEffect } from 'react';
import {
  Box,
  Container,
  Typography,
  Paper,
  Grid,
  Card,
  CardContent,
} from '@mui/material';
import { getStudentProfile, getStudentPerformance } from '../services/api';

const StudentDashboard: React.FC = () => {
  const [profile, setProfile] = useState<any>(null);
  const [performance, setPerformance] = useState<any>(null);

  useEffect(() => {
    fetchStudentData();
  }, []);

  const fetchStudentData = async () => {
    try {
      const profileData = await getStudentProfile();
      const performanceData = await getStudentPerformance();
      setProfile(profileData);
      setPerformance(performanceData);
    } catch (error) {
      console.error('Error fetching student data:', error);
    }
  };

  return (
    <Container>
      <Box sx={{ mt: 4 }}>
        <Typography variant="h4" gutterBottom>
          Student Dashboard
        </Typography>
        <Grid container spacing={3}>
          <Grid item xs={12} md={6}>
            <Paper sx={{ p: 3 }}>
              <Typography variant="h6" gutterBottom>
                Profile Information
              </Typography>
              {profile && (
                <>
                  <Typography>Full Name: {profile.fullName}</Typography>
                  <Typography>Email: {profile.email}</Typography>
                  <Typography>Student ID: {profile.id}</Typography>
                </>
              )}
            </Paper>
          </Grid>
          <Grid item xs={12} md={6}>
            <Paper sx={{ p: 3 }}>
              <Typography variant="h6" gutterBottom>
                Performance Overview
              </Typography>
              {performance && (
                <Grid container spacing={2}>
                  <Grid item xs={12}>
                    <Card>
                      <CardContent>
                        <Typography color="textSecondary" gutterBottom>
                          Overall Grade
                        </Typography>
                        <Typography variant="h5">
                          {performance.overallGrade}
                        </Typography>
                      </CardContent>
                    </Card>
                  </Grid>
                  {/* Add more performance metrics here */}
                </Grid>
              )}
            </Paper>
          </Grid>
        </Grid>
      </Box>
    </Container>
  );
};

export default StudentDashboard;
