// import React from 'react';
// import {
//   List,
//   ListItem,
//   ListItemText,
//   IconButton,
//   Divider,
// } from '@mui/material';
// import EditIcon from '@mui/icons-material/Edit';
// import { Template } from './types';

// interface TemplateListProps {
//   templates: Template[];
//   onEdit: (template: Template) => void;
// }

// const TemplateList: React.FC<TemplateListProps> = ({ templates, onEdit }) => {
//   return (
//     <List>
//       {templates.map((template) => (
//         <React.Fragment key={template.id}>
//           <ListItem
//             secondaryAction={
//               <IconButton
//                 edge="end"
//                 aria-label="edit"
//                 onClick={() => onEdit(template)}
//               >
//                 <EditIcon />
//               </IconButton>
//             }
//           >
//             <ListItemText
//               primary={template.name}
//               secondary={template.description}
//             />
//           </ListItem>
//           <Divider />
//         </React.Fragment>
//       ))}
//     </List>
//   );
// };

// export default TemplateList;
