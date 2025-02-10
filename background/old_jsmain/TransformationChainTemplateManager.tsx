// import React, { useState } from 'react';
// import { Container, Typography, Button, Dialog } from '@mui/material';
// import TemplateList from './TemplateList';


// const TransformationChainTemplateManager: React.FC = () => {
//   const [templates, setTemplates] = useState<Template[]>([]);
//   const [openForm, setOpenForm] = useState<boolean>(false);
//   const [editingTemplate, setEditingTemplate] = useState<Template | null>(null);

//   const handleCreate = () => {
//     setEditingTemplate(null);
//     setOpenForm(true);
//   };

//   const handleEdit = (template: Template) => {
//     setEditingTemplate(template);
//     setOpenForm(true);
//   };

//   const handleFormClose = () => {
//     setOpenForm(false);
//     setEditingTemplate(null);
//   };

//   const handleFormSubmit = (template: Template) => {
//     if (editingTemplate) {
//       // Update existing template
//       setTemplates((prev) =>
//         prev.map((t) => (t.id === template.id ? template : t))
//       );
//     } else {
//       // Add new template
//       setTemplates((prev) => [...prev, { ...template, id: Date.now() }]);
//     }
//     handleFormClose();
//   };

//   return (
//     <Container>
//       <Typography variant="h4" gutterBottom>
//         Transformation Chain Templates
//       </Typography>
//       <Button variant="contained" color="primary" onClick={handleCreate}>
//         Create New Template
//       </Button>
//       <TemplateList templates={templates} onEdit={handleEdit} />
//       <Dialog open={openForm} onClose={handleFormClose} fullWidth maxWidth="md">
//         <TemplateForm
//           template={editingTemplate}
//           onSubmit={handleFormSubmit}
//           onCancel={handleFormClose}
//         />
//       </Dialog>
//     </Container>
//   );
// };

// export default TransformationChainTemplateManager;
