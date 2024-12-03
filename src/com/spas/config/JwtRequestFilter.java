package com.spas.config;

import com.spas.service.UserService;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    
    private static final Logger logger = LoggerFactory.getLogger(JwtRequestFilter.class);
    private static final String BEARER_PREFIX = "Bearer ";
    
    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;
    
    @Autowired
    public JwtRequestFilter(UserService userService, JwtTokenUtil jwtTokenUtil) {
        this.userService = userService;
        this.jwtTokenUtil = jwtTokenUtil;
    }
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
            
        try {
            String jwt = extractJwtFromRequest(request);
            
            if (jwt != null) {
                processJwtAuthentication(jwt, request);
            }
            
            chain.doFilter(request, response);
            
        } catch (UsernameNotFoundException e) {
            logger.error("User not found: {}", e.getMessage());
            sendAuthenticationError(response, "Invalid credentials");
        } catch (Exception e) {
            logger.error("Authentication error: {}", e.getMessage());
            sendAuthenticationError(response, "Authentication failed");
        }
    }
    
    private String extractJwtFromRequest(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith(BEARER_PREFIX)) {
            return authHeader.substring(BEARER_PREFIX.length());
        }
        return null;
    }
    
    private void processJwtAuthentication(String jwt, HttpServletRequest request) {
        String username = jwtTokenUtil.extractUsername(jwt);
        
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userService.loadUserByUsername(username);
            
            if (jwtTokenUtil.validateToken(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authentication = createAuthenticationToken(userDetails, request);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                logger.debug("Authentication successful for user: {}", username);
            } else {
                logger.warn("Invalid JWT token for user: {}", username);
            }
        }
    }
    
    private UsernamePasswordAuthenticationToken createAuthenticationToken(UserDetails userDetails, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        return authentication;
    }
    
    private void sendAuthenticationError(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write("{\"error\": \"" + message + "\"}");
        response.getWriter().flush();
    }
}
