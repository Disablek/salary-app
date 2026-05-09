import React from 'react';
import { Container } from '@mui/material';

interface LayoutProps {
  children: React.ReactNode;
  maxWidth?: 'xs' | 'sm' | 'md' | 'lg' | 'xl' | false;
}

const Layout: React.FC<LayoutProps> = ({ children, maxWidth = 'lg' }) => {
  return (
    <Container maxWidth={maxWidth} sx={{ mt: 4, mb: 4 }}>
      {children}
    </Container>
  );
};

export default Layout;