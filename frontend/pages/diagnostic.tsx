import {
  Box,
  Button,
  Checkbox,
  IconButton,
  Input,
  Radio,
  RadioGroup,
  SimpleGrid,
  Stack,
  Text,
} from "@chakra-ui/react";
import FormInput from "../components/FormInput";
import { useState } from "react";
import { InfoIcon } from "@chakra-ui/icons";
import ProgressBar from "../components/ProgressBar";

const Diagnostic = () => {
  const [newDiagnostic, setNewDiagnostic] = useState(true);
  return (
    <Box backgroundColor="offwhite" h="100vh">
      <Box paddingTop="100px" marginX="310px" justifyContent="center">
        <Box display="flex" gap="12px">
          <Box
            height="56px"
            width="56px"
            borderRadius="full"
            backgroundColor="black"
          />
          <Box>
            <Text fontFamily="poppins.400" fontSize="lg">
              Sophia Iwara
            </Text>
            <Text fontFamily="poppins.400" color="gray" cursor="pointer">
              Ver perfil
            </Text>
          </Box>
        </Box>
        <Box
          marginTop="65px"
          backgroundColor="black"
          height="100px"
          borderRadius="8px"
          padding="20px"
          display="flex"
          justifyContent="space-between"
        >
          <Box>
            <Text color="white" fontFamily="inter.500" marginBottom="10px">
              Um guia para te situar
            </Text>
            <Text color="gray" fontSize="14px" fontFamily="inter.400">
              Descreva o que estiver sentindo e você receberá um relatório
              inteligente e instantâneo para ter uma base do que poderia estar
              ocorrendo.
            </Text>
          </Box>
          <IconButton
            isRound={true}
            variant="solid"
            colorScheme="white"
            aria-label="Done"
            fontSize="20px"
            icon={<InfoIcon />}
          />
        </Box>
        {newDiagnostic ? (
          <>
            <Text
              fontFamily="inter.400"
              fontSize="4xl"
              width="500px"
              marginTop="45px"
            >
              Entao... me diga, o que voce esta sentindo?
            </Text>
            <Input
              borderColor="black"
              borderWidth="2px"
              height="40px"
              width="50%"
              fontSize="14px"
              fontFamily="inter.400"
              placeholder="Dor de cabeça, dores nas costas..."
              marginBottom="15px"
              marginTop="70px"
            />
            <Box>
              <Button color="brand.900" backgroundColor="black" width="150px">
                Enviar
              </Button>
            </Box>
            <Text color="gray" fontSize="sm" width="60%" marginTop="220px">
              * Para obter um diagnóstico preciso e recomendações específicas, é
              importante consultar um médico. Além disso, se os sintomas
              persistirem ou piorarem, é essencial buscar atendimento médico
              imediatamente.
            </Text>
          </>
        ) : (
          <Box display="flex">
            <SimpleGrid columns={3} width="50%">
              <ProgressBar percentage={30} reason="Enxaqueca" />
              <ProgressBar percentage={50} reason="Má alimentação" />
              <ProgressBar percentage={60} reason="Falta de exercício" />
            </SimpleGrid>
            <Text>Descritivo</Text>
          </Box>
        )}
      </Box>
    </Box>
  );
};

export default Diagnostic;
