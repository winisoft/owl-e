


// // Mock user database
// const users: { [email: string]: { password: string, resetToken?: string, subscriptionStatus?: string, apiKey?: string } } = {};

// // Registration Endpoint
// app.post('/api/register', async (req, res) => {
//   const { email, password } = req.body;
//   if (!email || !password) {
//     return res.status(400).json({ message: 'Email and password are required' });
//   }

//   // Hash password
//   const hashedPassword = await bcrypt.hash(password, 10);
//   const apiKey = jwt.sign({ email }, process.env.JWT_SECRET as string, { expiresIn: '1y' });
//   users[email] = { password: hashedPassword, subscriptionStatus: 'registered', apiKey };
//   res.status(201).json({ message: 'User registered successfully', apiKey });
// });

// // Forgot Password Endpoint
// app.post('/api/forgot-password', forgotPasswordLimiter, recaptcha.middleware.verify, (req, res) => {
//   const { email } = req.body;
//   if (!email) {
//     return res.status(400).json({ message: 'Email is required' });
//   }

//   if (!req.recaptcha || req.recaptcha.error) {
//     return res.status(400).json({ message: 'CAPTCHA validation failed' });
//   }

//   const user = users[email];
//   if (user) {
//     // Generate a reset token
//     const resetToken = jwt.sign({ email }, process.env.JWT_SECRET as string, { expiresIn: '1h' });
//     user.resetToken = resetToken;

//     // Send reset email
//     const transporter = nodemailer.createTransport({
//       service: 'gmail',
//       auth: {
//         user: process.env.EMAIL_USER,
//         pass: process.env.EMAIL_PASS,
//       },
//     });

//     const mailOptions = {
//       from: process.env.EMAIL_USER,
//       to: email,
//       subject: 'Password Reset',
//       text: `You requested a password reset. Click the link to reset your password: ${process.env.CLIENT_URL}/reset-password?token=${resetToken}`,
//     };

//     transporter.sendMail(mailOptions, (error, info) => {
//       if (error) {
//         return res.status(500).json({ message: 'Error sending email' });
//       } else {
//         console.log('Email sent: ' + info.response);
//       }
//     });
//   }

//   res.status(200).json({ message: 'If the email is registered, a reset link will be sent' });
// });